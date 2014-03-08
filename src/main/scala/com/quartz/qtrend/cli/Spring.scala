package com.quartz.qtrend.cli

import org.springframework.context.annotation.{Bean, ComponentScan, AnnotationConfigApplicationContext, Configuration}
import org.springframework.context.support.AbstractApplicationContext
import com.quartz.qutilities.util.{QUserProperties, QProperties}
import javax.sql.DataSource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
@ComponentScan(Array("com.quartz.qtrend.cli"))
class Spring {

  @Bean
  def getProperties: QProperties = {
    println("Creating QProperties.....")
    QProperties.load {
      getClass.getResource("/env/default.properties")
    }
  }

  @Bean
  def getUserProperties: QUserProperties = new QUserProperties("qtrend-cli", true)

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

/**
 * Helper around
 */
object Spring {

  def apply[R](clazz: Class[R]): R = context.getBean(clazz)

  println("Creating Spring context....")
  val context: AbstractApplicationContext = new AnnotationConfigApplicationContext(classOf[Spring])
}

trait PropertiesField {
  implicit val properties = Spring(classOf[QProperties])
}

trait UserPropertiesField {
  implicit val userProperties = Spring(classOf[QUserProperties])
}
