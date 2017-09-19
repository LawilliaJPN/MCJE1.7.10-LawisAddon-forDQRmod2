package lawisAddonDqr2.config;

import java.util.Set;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import lawisAddonDqr2.LawisAddonDQR02;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

/*
 * ゲーム内でコンフィグを変更するためのGUI。
 *
 * 「TNT MODDERS」様の「MOD製作チュートリアル」を参考にさせていただきました。
 * https://www63.atwiki.jp/akasatanahama/pages/131.html
 */
public class Lad2GuiFactory implements IModGuiFactory {
	@Override
	public void initialize(Minecraft minecraftInstance) {
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return Lad2ConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

	public static class Lad2ConfigGui extends GuiConfig {
		public Lad2ConfigGui(GuiScreen parent) {
			super(parent, (new ConfigElement<Object>(Lad2ConfigCore.cfg.getCategory(Lad2ConfigCore.GENERAL))).getChildElements(), LawisAddonDQR02.MOD_ID, false, false, LawisAddonDQR02.MOD_NAME);
		}
	}
}