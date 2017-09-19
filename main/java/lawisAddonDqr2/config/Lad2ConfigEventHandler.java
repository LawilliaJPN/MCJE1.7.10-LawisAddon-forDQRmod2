package lawisAddonDqr2.config;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lawisAddonDqr2.LawisAddonDQR02;

/*
 * コンフィグ変更を反映させるイベント。
 */
public class Lad2ConfigEventHandler {
	/*
	 * コンフィグが変更されたときに呼び出されるメソッド
	 */
	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		// 変更されたコンフィグがこのmodのものであるとき
		if (event.modID.equals(LawisAddonDQR02.MOD_ID)) {
			syncConfigAndResetCount();
		}
	}

	/*
	 * 上記メソッドで呼び出されるメソッド。
	 * コンフィグ同期と関連変数のリセット。
	 */
	public static void syncConfigAndResetCount() {
		// コンフィグの同期を行う
		Lad2ConfigCore.syncConfig();
	}
}