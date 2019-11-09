package com.example

import akka.actor.{Actor, PoisonPill, Props}
import com.example.AkkaAuction.system
import com.example.AuctionAdmin.RunAuction
import akka.pattern.gracefulStop

object AuctionAdmin{

  case class AuctionEnded(winner: String, bid: Int)
  case object AuctionStarted
  case object RunAuction

  def props = Props[AuctionAdmin]
}

class AuctionAdmin extends Actor{
  import AuctionAdmin._
  import scala.concurrent.duration._

  val auctionActor = system.actorOf(AuctionManagerActor.props(self), "AuctionManager")

  val bidders =List.tabulate(3)(n =>  context.actorOf(BidderActor.props(auctionActor), s"bidder-$n"))
  //#actor-system

  //#main-send-messages

  override def receive: Receive = {
    case RunAuction =>
      auctionActor ! AuctionManagerActor.StartAuction

    case AuctionStarted =>
      bidders.foreach(_ ! BidderActor.DoBid)

    case AuctionEnded(n,b) =>
      println(s" And the winner is $n with a bid of: $b")
      bidders.foreach(_ ! PoisonPill)
    bidders.foreach(b =>context.stop(b))


  }
}
