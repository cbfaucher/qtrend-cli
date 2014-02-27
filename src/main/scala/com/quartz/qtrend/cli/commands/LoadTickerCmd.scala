package com.quartz.qtrend.cli.commands

import com.quartz.qutils.commands.CommandContext

/**
 * Created with IntelliJ IDEA.
 * User: Christian
 * Date: 2/27/14
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
object LoadTickerCmd {

  def apply(ctx: CommandContext) {
    println(s"Inside ${ctx.command.command}")
  }
}
