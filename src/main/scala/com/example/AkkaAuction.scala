//#full-example
package com.example


import akka.actor
import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

//#main-class
object AkkaAuction extends App {
  //#actor-system

  implicit val system: ActorSystem = actor.ActorSystem("AuctionSystem")
  implicit val ec: ExecutionContext = system.dispatcher


  val auctionActor = system.actorOf(AuctionManagerActor.props, "AuctionManager")
  //#actor-system

  //#main-send-messages
  auctionActor ! AuctionManagerActor.StartAuction
  //#main-send-messages
}
//#main-class
//#full-example
