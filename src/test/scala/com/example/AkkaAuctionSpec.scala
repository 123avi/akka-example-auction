//#full-example
package com.example

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActors, TestKit, TestProbe}
import com.example.AuctionAdmin.AuctionStarted
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

//#definition
class AkkaAuctionSpec  extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }


  "An Echo actor" must {

    "send back messages unchanged" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"
      expectMsg("hello world")
    }

  }

  "An Auction manager " must {
    "reply with AuctionStarted when getting startAuction command" in {
      val probe = TestProbe()
      val auctionManagerActor = system.actorOf(AuctionManagerActor.props(probe.ref))
      auctionManagerActor ! AuctionManagerActor.StartAuction
      probe.expectMsg(AuctionStarted)

    }
  }

}
//#full-example
