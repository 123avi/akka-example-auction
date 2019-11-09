package com.example

import akka.actor.{Actor, ActorRef, Props}
import com.example.AuctionManagerActor.EndAuction
import com.example.BidderActor.DoBid

import scala.util.Random

object BidderActor{
  def props(auction: ActorRef) = Props(new BidderActor(auction))
  case object DoBid
}
class BidderActor(auction: ActorRef) extends Actor{
  import context.dispatcher
  import scala.concurrent.duration._

  case object BidAnother
  override def receive: Receive = {
    case BidderActor.DoBid =>

      auction ! AuctionManagerActor.Bid(self.path.name, Random.nextInt(Integer.MAX_VALUE))

      context.system.scheduler.scheduleOnce(1 seconds, self, DoBid)

  }
}
