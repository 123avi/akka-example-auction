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


  val auctionAdmin = system.actorOf(AuctionAdmin.props, "auctionAdmin")
  //#actor-system

  //#main-send-messages
  auctionAdmin ! AuctionAdmin.RunAuction
  //#main-send-messages
}
//#main-class
//#full-example
