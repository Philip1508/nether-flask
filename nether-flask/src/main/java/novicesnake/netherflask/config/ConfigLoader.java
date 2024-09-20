package novicesnake.netherflask.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import novicesnake.netherflask.NetherFlask;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class ConfigLoader {

    private static final Path configDir = FabricLoader.getInstance().getConfigDir();
    private static final File configFile = new File(configDir + "/nether-flask.json");



    public static void initializeConfig()
    {
        Gson gson = new Gson();




        if (!configFile.exists())
        {
            try
            {
                NetherFlask.LOGGER.info("Created Nether Flask Config File at " + configDir.toString());
                configFile.createNewFile();

                FileWriter writer = new FileWriter(configFile);
                String jsonString = gson.toJson(NetherFlaskRuntimeData.stockConfiguration);
                writer.write(jsonString);
                writer.close();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        try(FileReader reader = new FileReader(configFile))
        {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            ConfigModel loadedConfig = gson.fromJson(jsonElement, ConfigModel.class);
            NetherFlaskRuntimeData.setLoadedConfiguration(loadedConfig);

            if (isInvalidConfig())
            {
                NetherFlaskRuntimeData.setLoadedConfiguration(NetherFlaskRuntimeData.stockConfiguration);
                overWriteConfig();
            }

        }
        catch (Exception e)
        {
            NetherFlask.LOGGER.error("Error loading Nether Flask Configuration.", e);

        }

    }





    private static boolean isInvalidConfig()
    {


        Field[] fields = NetherFlaskRuntimeData.loadedConfiguration.getClass().getDeclaredFields();


        boolean isDeformed = false;

        for (int i = 0; i < fields.length; i++)
        {
            fields[i].setAccessible(true);
            try {
                isDeformed |= fields[i].get(NetherFlaskRuntimeData.loadedConfiguration) == null;
            } catch (IllegalAccessException e) {
                return true;
            }
        }


        return isDeformed;
    }

    private static void overWriteConfig()
    {
        Gson gson = new Gson();

        try
        {
            NetherFlask.LOGGER.info("Overwrote Nether Flask Config File at " + configDir.toString());
            configFile.createNewFile();

            FileWriter writer = new FileWriter(configFile);
            String jsonString = gson.toJson(NetherFlaskRuntimeData.stockConfiguration);
            writer.write(jsonString);
            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
