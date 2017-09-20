package lawisAddonDqr2.event.spawn;

import java.util.Random;

import dqr.entity.mobEntity.monsterDay.DqmEntityKirikabuobake;
import dqr.entity.mobEntity.monsterHell.DqmEntityDarkdoriado;
import lawisAddonDqr2.config.Lad2ConfigCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Lad2SpawnFromBlock {
	public static void SpawnEnemyFromBlock (World world, EntityPlayer player, Block block, int x, int y, int z) {
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
		if (block instanceof BlockLog) {
			// 原木を破壊した時

			if (dif == 1) {
				// 昼⇒20分の1の確率で「きりかぶおばけ」
				int r = rand.nextInt(20);
				if (r == 0) entity = new DqmEntityKirikabuobake(world);

			} else if (dif == 2) {
				// 夜⇒2分の1の確率で「きりかぶおばけ」、20分の1の確率で「ダークドリアード」
				int r = rand.nextInt(20);
				if (r < 10) entity = new DqmEntityKirikabuobake(world);
				else if (r == 10) entity = new DqmEntityDarkdoriado(world);

			} else if (dif == 3) {
				// ネザー等⇒2分の1の確率で「ダークドリアード」、20分の1の確率で「きりかぶおばけ」
				int r = rand.nextInt(20);
				if (r < 10) entity = new DqmEntityDarkdoriado(world);
				else if (r == 10) entity = new DqmEntityKirikabuobake(world);
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
			player.addChatMessage(new ChatComponentTranslation(entity.getCommandSenderName() + "が あらわれた！"));
		}
	}
}
