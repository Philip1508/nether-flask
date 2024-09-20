package novicesnake.netherflask.config;






public class NetherFlaskRuntimeData {

    public static ConfigModel stockConfiguration = new ConfigModel();
    public static ConfigModel loadedConfiguration = new ConfigModel();


    public static void setLoadedConfiguration(ConfigModel model)
    {
        loadedConfiguration = model;
    }

}
