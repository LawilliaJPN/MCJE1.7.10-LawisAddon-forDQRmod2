package lawisAddonDqr2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lawisAddonDqr2.addon.Lad2Addons;
import lawisAddonDqr2.config.Lad2ConfigCore;
import lawisAddonDqr2.config.Lad2ConfigEventHandler;
import lawisAddonDqr2.config.Lad2InfoCore;

@Mod(modid = LawisAddonDQR02.MOD_ID, name = LawisAddonDQR02.MOD_NAME, version = LawisAddonDQR02.MOD_VERSION, guiFactory = "lawisAddonDqr2.config.Lad2GuiFactory")

public class LawisAddonDQR02 {
	public static final String MOD_ID = "LawisAddonDQR02";
	public static final String MOD_NAME = "Lawi's Addon for DQRmod 02";
	public static final String MOD_VERSION = "0.0.3";
	public static Logger logger = LogManager.getLogger("LawisAddonDQR02");

	@Metadata(MOD_ID)
	private static ModMetadata meta;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		// MODの情報の登録
		Lad2InfoCore.registerInfo(meta);
		// コンフィグの読み込み
		Lad2ConfigCore.loadConfig(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		// ゲーム内コンフィグ変更の反映
		FMLCommonHandler.instance().bus().register(new Lad2ConfigEventHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// DQRmodへのアドオン
		Lad2Addons.loadDQR();
	}
}