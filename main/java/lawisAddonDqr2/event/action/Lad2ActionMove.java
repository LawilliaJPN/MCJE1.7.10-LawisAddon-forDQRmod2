package lawisAddonDqr2.event.action;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class Lad2ActionMove {
	/*
	 * ダメージを受けた時に移動する
	 */
	public static void enemyHurtMove (EntityLivingBase enemy, DamageSource source) {
		Random rand = new Random();
		double mY = rand.nextDouble();
		if (enemy.posY <= 30) mY += 0.2D;
		if (mY < 0.5D) mY = 0.5D;

		// 炎系のダメージを受けた時
		if ((source == DamageSource.inFire) || (source == DamageSource.lava)) {
			enemy.motionY = mY;
			enemy.motionX += rand.nextDouble() *1 -0.5D;
			enemy.motionZ += rand.nextDouble() *1 -0.5D;
		}

		// サボテンからダメージを受けた時
		if (source == DamageSource.cactus) {
			enemy.motionX += rand.nextDouble() *2 -1;
			enemy.motionZ += rand.nextDouble() *2 -1;
		}

		// 壁の中で窒息した時
		if (source == DamageSource.inWall) {
			enemy.motionY = mY;
			enemy.motionX += rand.nextDouble() *2 -1;
			enemy.motionZ += rand.nextDouble() *2 -1;
		}

	}
}
