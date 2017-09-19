package lawisAddonDqr2.config;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lawisAddonDqr2.LawisAddonDQR02;
import net.minecraftforge.common.config.Configuration;

/*
 * コンフィグ設定の中心となるクラス。
 *
 * 「TNT MODDERS」様の「MOD製作チュートリアル」を参考にさせていただきました。
 * https://www63.atwiki.jp/akasatanahama/pages/131.html
 */
public class Lad2ConfigCore {
	public static Configuration cfg;

	/* GENERAL Mod全般の設定 */
	public static final String GENERAL = "General";

	// ACTION 追加行動
	private static final String ACTION = GENERAL + ".Action";
	public static boolean isConfigAction = true;

	// SPAWN 追加スポーン
	private static final String SPAWN = GENERAL + ".Spawn";
	public static boolean isConfigSpawn = true;

	// REWARD 追加報酬
	private static final String REWARD = GENERAL + ".Reward";
	public static boolean isConfigReward = true;


	/*
	 * preInitにて呼び出すメソッド。
	 * コンフィグの読み込み
	 */
	public static void loadConfig(FMLPreInitializationEvent event) {
		// Configurationのインスタンス化
		cfg = new Configuration(event.getSuggestedConfigurationFile(), LawisAddonDQR02.MOD_VERSION, true);

		// コンフィグの初期化・同期
		initConfig();
		syncConfig();
	}

	/*
	 * コンフィグの初期化
	 */
	private static void initConfig() {
		/* GENERAL Mod全般の設定 */
		cfg.addCustomCategoryComment(GENERAL, "The general settings of  " + LawisAddonDQR02.MOD_NAME + ".");
		cfg.setCategoryLanguageKey(GENERAL, "config.lad2.category.general");

		// ACTION 追加行動
		cfg.addCustomCategoryComment(ACTION, "The setting of Additonal Action.");
		cfg.setCategoryLanguageKey(ACTION, "config.lad2.category.action");

		// SPAWN 追加スポーン
		cfg.addCustomCategoryComment(SPAWN, "The setting of Additonal Spawn.");
		cfg.setCategoryLanguageKey(SPAWN, "config.lad2.category.spawn");

		// REWARD 追加報酬
		cfg.addCustomCategoryComment(REWARD, "The setting of Additonal Reward.");
		cfg.setCategoryLanguageKey(REWARD, "config.lad2.category.reward");
	}

	/*
	 * コンフィグの同期
	 */
	public static void syncConfig() {
		/* 変数への反映 */
		// ACTION 追加行動
		isConfigAction = cfg.getBoolean("Additional Action", ACTION, isConfigAction, "When this setting is true, actions are added to the DQRmod.", "config.lad2.category.action");
		// SPAWN 追加スポーン
		isConfigSpawn = cfg.getBoolean("Additional Spawn", SPAWN, isConfigSpawn, "When this setting is true, requirement to spawn are added to the DQRmod.", "config.lad2.category.spawn");
		// REWARD 追加報酬
		isConfigReward = cfg.getBoolean("Additional Reward", REWARD, isConfigReward, "When this setting is true, rewards are added to the DQRmod.", "config.lad2.category.reward");

		/* コンフィグファイルの保存 */
		cfg.save();
	}
}