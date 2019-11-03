package com.example

import akka.actor.{Actor, Props}

case object AuctionManagerActor{
  case object StartAuction
  case class Bid(name: String, bid:Int )
  case object EndAuction

  case object AuctionStarted
  def props = Props[AuctionManagerActor]
}


class AuctionManagerActor extends Actor{
  import AuctionManagerActor._
  override def receive: Receive = init

  def init:Receive = {
    case StartAuction =>
      sender ! AuctionStarted
      context.become(started())



    case other =>
      println(s"Got $other on init")
  }

  def started():Receive = ???
  //Add bidders

  /*
//Use the system's dispatcher as ExecutionContext
import system.dispatcher

//Schedules to send the "foo"-message to the testActor after 50ms
system.scheduler.scheduleOnce(50 milliseconds, testActor, "foo")
 */
  def ended:Receive = ???
}
