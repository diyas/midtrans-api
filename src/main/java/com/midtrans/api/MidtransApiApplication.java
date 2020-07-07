package com.midtrans.api;

import com.midtrans.api.model.SettingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "com.midtrans.api")
public class MidtransApiApplication extends SpringBootServletInitializer implements CommandLineRunner {

	private static ConfigurableApplicationContext context;

//	private static final Logger logger = LoggerFactory.getLogger(MidtransApiApplication.class);

	@Autowired
	private SettingProperties test;

	public static void main(String[] args) {
		System.out.println("Current Directory = " + System.getProperty("user.dir"));
		context = SpringApplication.run(MidtransApiApplication.class, args);
//		logger.error("just a test info log");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MidtransApiApplication.class);
	}

	public static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(MidtransApiApplication.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println("url: " + test.getApi().getBaseurl());
//		System.out.println("path: " + test.getApi().getPath().get(0).getLink());
//		System.out.println("Test: " + test.getApi().getPath().get(0).getMethod());
	}
}
