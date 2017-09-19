package lawisAddonDqr2.addon;

import cpw.mods.fml.common.Loader;
import lawisAddonDqr2.LawisAddonDQR02;
import lawisAddonDqr2.event.Lad2EventHandler;
import net.minecraftforge.common.MinecraftForge;

public class Lad2Addons {
	private static boolean DqrLoaded = false;

	/*
	 *  連携先のDQRmodが併用されているかを確認するメソッド
	 *  MOD ID：DQMIIINext
	 */
	public static void loadDQR() {
		if (Loader.isModLoaded("DQMIIINext")) {
			try {
				DqrLoaded = true;

				// イベント処理
				MinecraftForge.EVENT_BUS.register(new Lad2EventHandler());

			} catch (Throwable t) {
				LawisAddonDQR02.logger.warn("Failed to load DQRmod");
			}
		}
	}

	/*
	 *  getter
	 */
	public static boolean isDqrLoaded(){
		return DqrLoaded;
	}

}
