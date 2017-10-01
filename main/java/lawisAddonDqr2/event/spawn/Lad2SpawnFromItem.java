package lawisAddonDqr2.event.spawn;

import java.util.List;
import java.util.Random;

import dqr.entity.mobEntity.monsterDay.DqmEntityEbiruapple;
import dqr.entity.mobEntity.monsterNight.DqmEntityGappurin;
import lawisAddonDqr2.config.Lad2ConfigCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Lad2SpawnFromItem {
	public static void spawnEnemy (World world, EntityItem enItem, int x, int y, int z) {
		Random rand = new Random();
		EntityLiving entity = null;
		Item item = enItem.getEntityItem().getItem();

		// false にすると「～が あらわれた！」というログが表示されなくなる
		Boolean encounterLog = true;
		if (!Lad2ConfigCore.isConfigChat) encounterLog = false;

		// 難易度
		int dif = 0;
		if (world.provider.dimensionId == 0) {
			if (world.isDaytime()) dif = 1;
			else dif = 2;
		} else {
			dif = 3;
		}

		/* - - - - - - - - - - - - - - - - - - - -
		 * 以下、どの敵をスポーンさせるか決める処理
		 * - - - - - - - - - - - - - - - - - - - */
		if ((item == Items.apple) || (item == Items.golden_apple)) {
			// リンゴか金リンゴが消滅した時

			if (dif == 1) {
				// 昼⇒20分の1の確率で「エビルアップル」
				int r = rand.nextInt(20);
				if (r == 0) entity = new DqmEntityEbiruapple(world);

			} else if (dif == 2) {
				// 夜⇒2分の1の確率で「エビルアップル」、20分の1の確率で「ガップリン」
				int r = rand.nextInt(20);
				if (r < 10) entity = new DqmEntityEbiruapple(world);
				else if (r == 10) entity = new DqmEntityGappurin(world);

			} else if (dif == 3) {
				// ネザー等⇒2分の1の確率で「ガップリン」、20分の1の確率で「エビルアップル」
				int r = rand.nextInt(20);
				if (r < 10) entity = new DqmEntityGappurin(world);
				else if (r == 10) entity = new DqmEntityEbiruapple(world);
			}
		}

		/* - - - - - - - - - - - - - - - - - - - -
		 * 以下、決めた敵をスポーンさせる処理
		 * - - - - - - - - - - - - - - - - - - - */

		// スポーンする敵が設定されていない時は中断
		if (entity == null) return;

		// 敵の位置や向きを設定
		entity.setLocationAndAngles(x +0.5D, y, z +0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
		entity.rotationYawHead = entity.rotationYaw;
		entity.renderYawOffset = entity.rotationYaw;

		// 敵の情報をスポーンエッグでスポーンする時の様にリセットする
		entity.onSpawnWithEgg((IEntityLivingData)null);

		// 敵をスポーンさせる
		if (!world.isRemote) {
			world.spawnEntityInWorld(entity);
		}

		// 効果音を鳴らす
		entity.playLivingSound();

		// ログに敵がスポーンしたことを表示する
		if (encounterLog) {
			// プレイヤーの周囲のEntityをListに入れる
			List list = enItem.worldObj.getEntitiesWithinAABBExcludingEntity(enItem,
					enItem.boundingBox.addCoord(enItem.motionX, enItem.motionY, enItem.motionZ).expand(10.0D, 5.0D, 10.0D));

			// Listが空の場合とnullの場合を除く
	        if (list != null && !list.isEmpty()) {
	        	// Listに入っているEntityを一つずつ確認
	        	for (int n = 0 ; n < list.size() ; n++) {
	        		Entity target = (Entity)list.get(n);

	        		if (target != null) {
	        			// Entityがプレイヤーだった場合
	        			if(target instanceof EntityPlayer) {
	        				// 以下、自分のペットかどうかの確認
	        				EntityPlayer player = (EntityPlayer)target;
	        				player.addChatMessage(new ChatComponentTranslation(entity.getCommandSenderName() + "が あらわれた！"));
						}
	        		}
	        	}
	        }
		}
	}
}
