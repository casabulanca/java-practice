package skynet.ant.rpc.ice;

public class IceConfig {

	/**
	 * 后期根据需要，采用可配置的方式
	 */
	private static Ice.InitializationData initData;

	static {
		// 加载属性文件
		Ice.Properties beforeProperties = Ice.Util.createProperties();
		beforeProperties.setProperty("Ice.MessageSizeMax", "1073741824");// 1024*1024*1024;
		 beforeProperties.setProperty("Ice.ThreadPool.Server.Size", "200");
		 beforeProperties.setProperty("Ice.ThreadPool.Client.Size", "200");
		 beforeProperties.setProperty("Ice.ThreadPool.Server.SizeMax", "200");
		 beforeProperties.setProperty("Ice.ThreadPool.Client.SizeMax", "200");
		 beforeProperties.setProperty("Ice.ThreadPool.Server.SizeWarn", "200");
		 beforeProperties.setProperty("Ice.ThreadPool.Client.SizeWarn", "200");

		initData = new Ice.InitializationData();
		initData.properties = beforeProperties;
		// System.out.println(beforeProperties.getProperty("Ice.MessageSizeMax"));
	}

	/**
	 * 
	 * @return
	 */
	public static Ice.InitializationData getInitializationData() {

		return initData;
	}

}
