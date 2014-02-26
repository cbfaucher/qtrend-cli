package com.quartz.qtrend.cli

import javax.sql.DataSource
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.Connection
import java.io.PrintWriter
import java.util.logging.Logger
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.util.Properties
import com.quartz.qutilities.util.QProperties

/**
 * Created with IntelliJ IDEA.
 * User: Christian
 * Date: 2/24/14
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@ComponentScan(Array("com.quartz.qtrend.cli"))
class Application {

  @Bean
  def getProperties: QProperties = QProperties.load {
    getClass.getResource("/env/default.properties")
  }

  @Bean
  def getDataSource: DataSource = {
    Class.forName(getProperties.getProperty("jdbc.driver"))

    new DriverManagerDataSource(
      getProperties.getProperty("jdbc.url"),
      getProperties.getProperty("jdbc.user"),
      getProperties.getProperty("jdbc.password"))
  }

  @Bean
  def getJdbcTemplate: JdbcTemplate = new JdbcTemplate(getDataSource)
}
