package com.tmdrk.chat.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Title:ConfigUtils Description:配置文件读取
 *
 * @Edit_Description:
 * @version:shebao-framelib 1.0
 */
public class ConfigUtils {

    private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    /**
     * 读取Properties配置文件
     *
     * @param confPath 配置文件路径
     * @Edit_Description:
     * @Create_Version:shebao-framelib 1.0
     */
    public static Properties getPropertiesFile(String confPath) {
        Properties prop = new Properties();
        try {
            InputStreamReader is = new InputStreamReader(ConfigUtils.class
                .getClassLoader().getResourceAsStream(confPath), "UTF-8");
            prop.load(is);
            is.close();
            if (logger.isDebugEnabled()) {
                logger.debug("load config file success ,filePath is "
                    + confPath);
            }
        } catch (Exception ex) {
            logger.info("try  FileInputStream  load" );
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream( "./" + confPath );
                if (fileInputStream != null) {
                    prop.load( fileInputStream );
                    fileInputStream.close();
                    logger.info("加载成功" );
                    return prop;
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("load config file success ,filePath is "
                        + confPath);
                }
            } catch (Exception exception) {
                if (logger.isErrorEnabled()) {
                    logger.error("error properties==>" + confPath, ex);
                }
            }
        }
        return prop;
    }

}
