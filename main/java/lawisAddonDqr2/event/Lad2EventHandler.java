package lawisAddonDqr2.event;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dqr.entity.mobEntity.DqmMobBase;
import lawisAddonDqr2.config.Lad2ConfigCore;
import lawisAddonDqr2.event.action.Lad2ActionBreakBlock;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class Lad2EventHandler {
	/*
	 * プレイヤーがEntityに攻撃した時に呼び出される処理
	 * MinecraftForge.EVENT_BUS.registerで呼び出されるので、staticを付けずに@SubscribeEventを付ける
	 *
	 * コンフィグ：追加行動がオンの時に、プレイヤーに攻撃された敵が周囲のブロックを破壊するようになる。
	 */
	@SubscribeEvent
	public void PlayerAttackEvent(AttackEntityEvent event) {
		// System.out.println("PlayerAttackEvent OK");

		// ピースフルの時は、このイベントは動作しない
		if (event.entityPlayer.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) return;

		// コンフィグ：追加行動がオフの時は動作しない
		if (!Lad2ConfigCore.isConfigAction) return;

		// DQRmodの敵を攻撃した時
		if ((event.target instanceof DqmMobBase)) {
			// 周囲のブロックを破壊する
			Lad2ActionBreakBlock.enemyBreakBlock((DqmMobBase)event.target, event.entityPlayer);
		}
	}

	/*
	 * Entityがダメージを受けた時に呼び出される処理
	 * MinecraftForge.EVENT_BUS.registerで呼び出されるので、staticを付けずに@SubscribeEventを付ける
	 *
	 * コンフィグ：追加行動がオンになるときに、DQRの敵が溶岩などからダメージを受け続けないように移動する。
	 */
	@SubscribeEvent
	public void EnemyHurtEvent(LivingHurtEvent event) {
		// ピースフルの時は、このイベントは動作しない
		if (event.entity.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) return;

		// コンフィグ：追加行動がオフの時は動作しない
		if (!Lad2ConfigCore.isConfigAction) return;

		// ダメージが1.0未満なら動作しない
		if (event.ammount < 1.0F) return;

		// DQRmodの敵がダメージを受けた時
		if ((event.entityLiving instanceof DqmMobBase)) {
			EntityLivingBase enemy = event.entityLiving;

			Random rand = new Random();
			double mY = rand.nextDouble();
			if (event.entityLiving.posY <= 30) mY += 0.2D;
			if (mY < 0.5D) mY = 0.5D;

			// 炎系のダメージを受けた時
			if ((event.source == DamageSource.inFire) || (event.source == DamageSource.lava)) {
				enemy.motionY = mY;
				enemy.motionX += rand.nextDouble() *1 -0.5D;
				enemy.motionZ += rand.nextDouble() *1 -0.5D;
			}

			// サボテンからダメージを受けた時
			if (event.source == DamageSource.cactus) {
				enemy.motionX += rand.nextDouble() *2 -1;
				enemy.motionZ += rand.nextDouble() *2 -1;
			}

			// 壁の中で窒息した時
			if (event.source == DamageSource.inWall) {
				enemy.motionY = mY;
				enemy.motionX += rand.nextDouble() *2 -1;
				enemy.motionZ += rand.nextDouble() *2 -1;
			}
		}
	}
}
