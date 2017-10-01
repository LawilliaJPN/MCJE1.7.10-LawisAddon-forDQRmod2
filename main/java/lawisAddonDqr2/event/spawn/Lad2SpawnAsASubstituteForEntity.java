package lawisAddonDqr2.event.spawn;

import java.util.List;
import java.util.Random;

import dqr.api.potion.DQPotionPlus;
import dqr.entity.mobEntity.monsterDay.DqmEntityAyasiikage;
import dqr.entity.mobEntity.monsterDay.DqmEntityDoronuba;
import dqr.entity.mobEntity.monsterDay.DqmEntityGuntaigani;
import dqr.entity.mobEntity.monsterDay.DqmEntityHoimisura;
import dqr.entity.mobEntity.monsterDay.DqmEntityOonamekuji;
import dqr.entity.mobEntity.monsterDay.DqmEntitySibirekurage;
import dqr.entity.mobEntity.monsterDay.DqmEntitySuraimutumuri;
import dqr.entity.mobEntity.monsterDay.DqmEntityUzusioking;
import dqr.entity.mobEntity.monsterEnd.DqmEntityRyuiso;
import dqr.entity.mobEntity.monsterHell.DqmEntityBehomasuraimu;
import dqr.entity.mobEntity.monsterHell.DqmEntityGanirasu;
import dqr.entity.mobEntity.monsterHell.DqmEntityJigokunohasami;
import dqr.entity.mobEntity.monsterHell.DqmEntityKirakurabu;
import dqr.entity.mobEntity.monsterHell.DqmEntityMaounokage;
import dqr.entity.mobEntity.monsterHell.DqmEntityPombom;
import dqr.entity.mobEntity.monsterHell.DqmEntityPuyon;
import dqr.entity.mobEntity.monsterHell.DqmEntityUmibouzu;
import dqr.entity.mobEntity.monsterNight.DqmEntityBaburin;
import dqr.entity.mobEntity.monsterNight.DqmEntityBehoimisuraimu;
import dqr.entity.mobEntity.monsterNight.DqmEntityBehoimusuraimu;
import dqr.entity.mobEntity.monsterNight.DqmEntityHoroghost;
import dqr.entity.mobEntity.monsterNight.DqmEntityJeriman;
import dqr.entity.mobEntity.monsterNight.DqmEntityMarinsuraimu;
import dqr.entity.mobEntity.monsterNight.DqmEntitySyado;
import dqr.entity.mobEntity.monsterNight.DqmEntityUmiusi;
import lawisAddonDqr2.config.Lad2ConfigCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Lad2SpawnAsASubstituteForEntity {
	public static void spawnEnemy (World world, Entity entity, int x, int y, int z) {
		Random rand = new Random();
		EntityLiving enemy = null;

		// false にすると「～が あらわれた！」というログが表示されなくなる
		Boolean encounterLog = true;
		if (!Lad2ConfigCore.isConfigChat) encounterLog = false;

		// ポーション付与用
		Boolean subayasaNoTane = false;

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
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase)entity;

			if (living instanceof EntitySquid) {
				// イカがスポーンした時
				if (world.isDaytime()) {
					if (rand.nextInt(10) == 0) {
						int r = rand.nextInt(13);

						if (r == 0) enemy = new DqmEntityGuntaigani(world);
						else if (r == 1) enemy = new DqmEntitySibirekurage(world);
						else if (r == 2) enemy = new DqmEntityHoimisura(world);
						else if (r == 3) enemy = new DqmEntityBehoimisuraimu(world);
						else if (r == 4) enemy = new DqmEntityBehoimusuraimu(world);
						else if (r == 5) enemy = new DqmEntityUzusioking(world);
						else if (r == 6) enemy = new DqmEntityOonamekuji(world);
						else if (r == 7) enemy = new DqmEntityUmiusi(world);
						else if (r == 8) enemy = new DqmEntitySuraimutumuri(world);
						else if (r == 9) enemy = new DqmEntityMarinsuraimu(world);
						else if (r == 10) enemy = new DqmEntityMarinsuraimu(world);
						else if (r == 11) enemy = new DqmEntityDoronuba(world);
						else if (r == 12) enemy = new DqmEntityJeriman(world);
					}

				} else {
					if (rand.nextInt(10) == 0) {
						int r = rand.nextInt(25);

						if (r == 0) enemy = new DqmEntityGuntaigani(world);
						else if (r == 1) enemy = new DqmEntityGanirasu(world);
						else if (r == 2) enemy = new DqmEntityKirakurabu(world);
						else if (r == 3) enemy = new DqmEntityJigokunohasami(world);
						else if (r == 4) enemy = new DqmEntitySibirekurage(world);
						else if (r == 5) enemy = new DqmEntityHoimisura(world);
						else if (r == 6) enemy = new DqmEntityBehoimisuraimu(world);
						else if (r == 7) enemy = new DqmEntityBehoimusuraimu(world);
						else if (r == 8) enemy = new DqmEntityBehomasuraimu(world);
						else if (r == 9) enemy = new DqmEntityUzusioking(world);
						else if (r == 10) enemy = new DqmEntityOonamekuji(world);
						else if (r == 11) enemy = new DqmEntityUmiusi(world);
						else if (r == 12) enemy = new DqmEntityAyasiikage(world);
						else if (r == 13) enemy = new DqmEntitySyado(world);
						else if (r == 14) enemy = new DqmEntityHoroghost(world);
						else if (r == 15) enemy = new DqmEntityMaounokage(world);
						else if (r == 16) enemy = new DqmEntitySuraimutumuri(world);
						else if (r == 17) enemy = new DqmEntityMarinsuraimu(world);
						else if (r == 18) enemy = new DqmEntityDoronuba(world);
						else if (r == 19) enemy = new DqmEntityJeriman(world);
						else if (r == 20) enemy = new DqmEntityUmibouzu(world);
						else if (r == 21) enemy = new DqmEntityBaburin(world);
						else if (r == 22) enemy = new DqmEntityPuyon(world);
						else if (r == 23) enemy = new DqmEntityPombom(world);
						else if (r == 24) enemy = new DqmEntityRyuiso(world);
					}
				}
				encounterLog = false;
				subayasaNoTane = true;
			}
		}


		/* - - - - - - - - - - - - - - - - - - - -
		 * 以下、決めた敵をスポーンさせる処理
		 * - - - - - - - - - - - - - - - - - - - */

		// スポーンする敵が設定されていない時は中断
		if (enemy == null) return;

		// 敵の位置や向きを設定
		enemy.setLocationAndAngles(x +0.5D, y, z +0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
		enemy.rotationYawHead = enemy.rotationYaw;
		enemy.renderYawOffset = enemy.rotationYaw;

		// 敵の情報をスポーンエッグでスポーンする時の様にリセットする
		enemy.onSpawnWithEgg((IEntityLivingData)null);

		// 敵をスポーンさせる
		if (!world.isRemote) {
			world.spawnEntityInWorld(enemy);

			// ポーション付与
			if (subayasaNoTane) enemy.addPotionEffect(new PotionEffect(DQPotionPlus.potionSubayasanotane.id, 20 * 60, 2));
		}

		// 効果音を鳴らす
		enemy.playLivingSound();

		// ログに敵がスポーンしたことを表示する
		if (encounterLog) {
			// プレイヤーの周囲のEntityをListに入れる
			List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity,
					entity.boundingBox.addCoord(entity.motionX, entity.motionY, entity.motionZ).expand(10.0D, 5.0D, 10.0D));

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

		// 元のentityをデスポーンさせる
		entity.setDead();
	}
}
