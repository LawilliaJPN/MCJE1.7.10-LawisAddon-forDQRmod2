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
	public static boolean isConfigAction = false;

	// SPAWN 追加スポーン
	private static final String SPAWN = GENERAL + ".Spawn";
	public static boolean isConfigSpawn = false;

	// REWARD 追加報酬
	private static final String REWARD = GENERAL + ".Reward";
	public static boolean isConfigReward = false;


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
		cfg.setCategoryLanguageKey(GENERAL, "config.lad.category.general");

		// ACTION 負荷軽減
		cfg.addCustomCategoryComment(ACTION, "The setting of Additonal Action.");
		cfg.setCategoryLanguageKey(ACTION, "config.lad.category.action");
	}

	/*
	 * コンフィグの同期
	 */
	public static void syncConfig() {
		/* 変数への反映 */
		// REDUCTION 負荷軽減
		isConfigAction = cfg.getBoolean("Workload Reduction", ACTION, isConfigAction, "When this setting is true, large rooms become smaller for workload reduction.", "config.lad.category.action");

		/* コンフィグファイルの保存 */
		cfg.save();
	}
}