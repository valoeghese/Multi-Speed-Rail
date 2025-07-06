package nz.valoeghese.railmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class MultispeedRail implements ModInitializer {
	public static final String MOD_ID = "multi-speed-rail";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final Properties CONFIG = new Properties();

	private static double mediumRailSpeed = 13.9; // 50km/h
	private static double highRailSpeed = 27.8; // 100km/h
	private static double deceleration = 6;

	static {
		CONFIG.setProperty("medium-rail-speed", String.valueOf(mediumRailSpeed));
		CONFIG.setProperty("high-rail-speed", String.valueOf(highRailSpeed));
		CONFIG.setProperty("deceleration", String.valueOf(deceleration));
	}

	public static double getMediumRailSpeed() {
		return mediumRailSpeed;
	}

	public static double getHighRailSpeed() {
		return highRailSpeed;
	}

	public static double getDeceleration() {
		return deceleration;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Multi Speed Rail is installed.");

		Path config = FabricLoader.getInstance().getConfigDir().resolve("multi-speed-rail.txt");
		if (Files.exists(config)) {
			try (BufferedReader br = Files.newBufferedReader(config)) {
				CONFIG.load(br);
			} catch (IOException e) {
				throw new UncheckedIOException("Failed to load Multi Speed Rail config", e);
			}

			// parse config
			try {
				mediumRailSpeed = Double.parseDouble(CONFIG.getProperty("medium-rail-speed"));
			} catch (NumberFormatException | NullPointerException e) {
				LOGGER.warn("Failed to load medium rail speed", e);
			}
			try {
				highRailSpeed = Double.parseDouble(CONFIG.getProperty("high-rail-speed"));
			} catch (NumberFormatException | NullPointerException e) {
				LOGGER.warn("Failed to load high rail speed", e);
			}
			try {
				deceleration = Double.parseDouble(CONFIG.getProperty("deceleration"));
			} catch (NumberFormatException | NullPointerException e) {
				LOGGER.warn("Failed to load rail deceleration", e);
			}
			LOGGER.info("Loaded Multi Speed Rail config");
		} else {
			try (BufferedWriter bw = Files.newBufferedWriter(config)) {
				CONFIG.store(bw, "Multi Speed Rail config. Units are in m/s (blocks/s)");
			} catch (IOException e) {
				throw new UncheckedIOException("Failed to save Multi Speed Rail config", e);
			}
		}
	}
}