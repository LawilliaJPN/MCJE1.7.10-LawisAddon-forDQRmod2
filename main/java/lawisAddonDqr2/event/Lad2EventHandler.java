package lawisAddonDqr2.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dqr.api.Items.DQMiscs;
import dqr.entity.mobEntity.DqmMobBase;
import dqr.entity.petEntity.DqmPetBase;
import lawisAddonDqr2.config.Lad2ConfigCore;
import lawisAddonDqr2.event.action.Lad2ActionBreakBlock;
import lawisAddonDqr2.event.action.Lad2ActionMove;
import lawisAddonDqr2.event.spawn.Lad2SpawnFromBlock;
import lawisAddonDqr2.event.spawn.Lad2SpawnFromEntity;
import lawisAddonDqr2.event.spawn.Lad2SpawnFromItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class Lad2EventHandler {
	/* 追加行動 */

	/*
	 * プレイヤーがEntityに攻撃した時に呼び出される処理
	 * MinecraftForge.EVENT_BUS.registerで呼び出されるので、staticを付けずに@SubscribeEventを付ける
	 *
	 * コンフィグ：追加行動がオン⇒プレイヤーに攻撃されたDQRmodのモンスターが周囲のブロックを破壊。
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
	 * コンフィグ：追加行動がオン⇒DQRmodの敵が溶岩などからダメージを受け続けないように移動。
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
			Lad2ActionMove.enemyHurtMove(event.entityLiving, event.source);
		}
	}

	/* 追加スポーン */

	/*
	 * ブロックが破壊された時に呼び出される処理
	 * MinecraftForge.EVENT_BUS.registerで呼び出されるので、staticを付けずに@SubscribeEventを付ける
	 *
	 * コンフィグ：追加スポーンがオン⇒特定ブロックを破壊した時に一定確率で敵がスポーンする。
	 */
	@SubscribeEvent
	public void BreakBlockEvent(BreakEvent event) {
		// ピースフルの時、このイベントは動作しない
		if (event.world.difficultySetting == EnumDifficulty.PEACEFUL) return;

		// コンフィグ：追加スポーンがオフの時、このイベントは動作しない
		if (!Lad2ConfigCore.isConfigSpawn) return;

		Lad2SpawnFromBlock.SpawnEnemyFromBlock(event.world, event.getPlayer(), event.block, event.x, event.y, event.z);
	}

	/*
	 * 敵が死亡したときに呼び出される処理
	 * MinecraftForge.EVENT_BUS.registerで呼び出されるので、staticを付けずに@SubscribeEventを付ける
	 *
	 * コンフィグ：追加スポーンがオン⇒特定ブロックを破壊した時に一定確率で敵がスポーンする。
	 */
	@SubscribeEvent
	public void EnemyDeathEvent(LivingDeathEvent event) {
		// ピースフルの時、このイベントは動作しない
		if (event.entityLiving.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) return;

		// コンフィグ：追加スポーンがオフの時、このイベントは動作しない
		if (!Lad2ConfigCore.isConfigSpawn) return;

		Lad2SpawnFromEntity.SpawnEnemyFromEntity(event.entityLiving.worldObj, event.entityLiving, (int)event.entityLiving.posX, (int)event.entityLiving.posY, (int)event.entityLiving.posZ);
	}

	/*
	 * EntityItemがデスポーンする時に呼び出される処理
	 * MinecraftForge.EVENT_BUS.registerで呼び出されるので、staticを付けずに@SubscribeEventを付ける
	 *
	 * コンフィグ：追加スポーンがオン⇒特定ブロックを破壊した時に一定確率で敵がスポーンする。
	 */
	@SubscribeEvent
	public void ItemDespawnEvent(ItemExpireEvent event) {
		// ピースフルの時、このイベントは動作しない
		if (event.entityItem.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) return;

		// コンフィグ：追加スポーンがオフの時、このイベントは動作しない
		if (!Lad2ConfigCore.isConfigSpawn) return;

		Lad2SpawnFromItem.SpawnEnemyFromItem(event.entityItem.worldObj, event.entityItem, (int)event.entityItem.posX, (int)event.entityItem.posY, (int)event.entityItem.posZ);
	}

	/* 追加報酬 */

	/*
	 * Entityがダメージを受けた時に呼び出される処理
	 * MinecraftForge.EVENT_BUS.registerで呼び出されるので、staticを付けずに@SubscribeEventを付ける
	 *
	 * コンフィグ：追加報酬がオン⇒DQRmodのペットがDQRmodの敵を倒したときに報酬追加。
	 */
	@SubscribeEvent
	public void EnemyAdditionalDrops(LivingDropsEvent event){
		// ピースフルの時、このイベントは動作しない
		if (event.entity.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) return;

		// コンフィグ：追加報酬がオフの時、このイベントは動作しない
		if (!Lad2ConfigCore.isConfigReward) return;

		// 死亡したEntityが、DQRmodの敵の場合
    	if (event.entityLiving instanceof DqmMobBase) {
    		// 倒したEntityが、DQRmodのペットの場合
    		if (event.source.getSourceOfDamage() instanceof DqmPetBase) {
    			World world = event.entityLiving.worldObj; // EntityItemの第1引数
    	        double x = event.entityLiving.posX; // EntityItemの第2引数
    	        double y = event.entityLiving.posY; // EntityItemの第3引数
    	        double z = event.entityLiving.posZ; // EntityItemの第4引数

    	        // 100分の1の確率で
    	        int r100 = new java.util.Random().nextInt(100);
    	        if (r100 == 0) {
    	        	// 「せかいじゅの葉」を落とす
    	        	if(!event.entityLiving.worldObj.isRemote) {
        	        	event.drops.add(new EntityItem(world, x, y, z, new ItemStack(DQMiscs.itemSekaijunoha)));
        	        }
    	        }
    		}
    	}
	}
}
