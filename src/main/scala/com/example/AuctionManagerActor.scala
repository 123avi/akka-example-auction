package com.example

import akka.actor.{Actor, ActorRef, Props}
import com.example.AuctionAdmin.{AuctionEnded, AuctionStarted}

case object AuctionManagerActor{
  case object StartAuction
  case class Bid(name: String, bid:Int )
  case object EndAuction

  def props(replyTo: ActorRef) = Props(new AuctionManagerActor(replyTo))
}


class AuctionManagerActor(replyTo: ActorRef) extends Actor{
  import AuctionManagerActor._
  import scala.concurrent.duration._
  import context.dispatcher



  override def receive: Receive = init

  def init:Receive = {
    case StartAuction =>
//      sender ! AuctionStarted
      context.become(started(None))
      context.system.scheduler.scheduleOnce(10 seconds, self, EndAuction)

      sender ! AuctionStarted

    case other =>
      println(s"Got $other on init")
  }

  def started(max:Option[Bid]):Receive = {
    case EndAuction =>
      println(s"The winner is $max")
//      context.system.shutdown()
      context.become(ended(max))
      max match {
        case Some(Bid(n,b)) =>
          replyTo ! AuctionEnded(n,b)
        case None =>
          replyTo ! AuctionEnded("No Winner", 0)
      }


    case bd @ Bid(_, b) =>
      println(s"got bid $bd")

      val m = max match {
        case Some(value) =>
          if (value.bid < b)
            Some(bd)
          else
            Option(value)
        case None =>Some(bd)
      }
//      println(s"current winner $m")
      context.become(started(m))

  }
  //Add bidders

  /*
//Use the system's dispatcher as ExecutionContext
import system.dispatcher

//Schedules to send the "foo"-message to the testActor after 50ms
system.scheduler.scheduleOnce(50 milliseconds, testActor, "foo")
 */
  def ended(max:Option[Bid]):Receive = {
    case other =>
      println(s"Got $other but the winner is $max")
  }
}
