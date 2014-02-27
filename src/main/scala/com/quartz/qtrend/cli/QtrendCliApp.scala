package com.quartz.qtrend.cli

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import com.quartz.qutils.commands.{Command, CommandManager}
import Command._
import com.quartz.qtrend.cli.commands.LoadTickerCmd

/**
 * Entry Point for QTrend-CLI
 */
object QtrendCliApp {

  private val commandManager = new CommandManager(
    findCommand, helpCommand, exitCommand,
    Command("load ticker", "<ticker>: Loads given ticker", LoadTickerCmd.apply)
  )

  val context = new AnnotationConfigApplicationContext(classOf[TestSpringApp])

  private[this] def startConsole() {

    println()
    println("WELCOME TO QTREND CLI!")
    println("----------------------")
    println()
    println("Type 'help' to start...")
    println()

    while(true) {
      val line = readLine("$ ")
      commandManager(line)
    }
  }

  def main(args: Array[String]) {
    //  check if interactive
    if (args.toSeq.exists(_ == "--interactive")) {
      startConsole()
    } else {
      if (args.length > 0) {
        //  execute arguments as a command
        commandManager(args.mkString(" "))
      } else {
        println(s"java ${getClass.getName} <--interactive|help|command keywords>")
        println("- --interactive: Starts console.")
        println("- help         : Shows available commands.")
        println("- cmd          : Executes the given commands.")
      }
    }
  }
}
