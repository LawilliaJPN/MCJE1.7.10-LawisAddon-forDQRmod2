package lawisAddonDqr2.event.spawn;

import java.util.List;
import java.util.Random;

import dqr.entity.mobEntity.monsterEnd.DqmEntityDesujakkaru;
import dqr.entity.mobEntity.monsterHell.DqmEntityBariidodog;
import dqr.entity.mobEntity.monsterNight.DqmEntityAnimaruzonbi;
import lawisAddonDqr2.config.Lad2ConfigCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Lad2SpawnFromEntity {
	public static void SpawnEnemyFromEntity (World world, EntityLivingBase enemy, int x, int y, int z) {
		Random rand = new Random();
		EntityLiving entity = null;

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
		if (enemy instanceof EntityWolf) {
			// オオカミが死亡した時

			if (dif == 1) {
				// 昼⇒20分の1の確率で「アニマルゾンビ」
				int r = rand.nextInt(20);
				if (r == 0) entity = new DqmEntityAnimaruzonbi(world);

			} else if (dif == 2) {
				// 夜⇒2分の1の確率で「アニマルゾンビ」、20分の1の確率で「バリイドドッグ」
				int r = rand.nextInt(20);
				if (r < 10) entity = new DqmEntityAnimaruzonbi(world);
				else if (r == 10) entity = new DqmEntityBariidodog(world);

			} else if (dif == 3) {
				// ネザー等⇒2分の1の確率で「バリイドドッグ」、20分の1の確率で「デスジャッカル」
				int r = rand.nextInt(20);
				if (r < 10) entity = new DqmEntityBariidodog(world);
				else if (r == 10) entity = new DqmEntityDesujakkaru(world);
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
			List list = enemy.worldObj.getEntitiesWithinAABBExcludingEntity(enemy,
					enemy.boundingBox.addCoord(enemy.motionX, enemy.motionY, enemy.motionZ).expand(10.0D, 5.0D, 10.0D));

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
