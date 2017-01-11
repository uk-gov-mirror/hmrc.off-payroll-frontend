/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.offpayroll.models

import org.scalatest.{FlatSpec, Matchers}
import uk.gov.hmrc.offpayroll.resources._

/**
  * Created by peter on 11/01/2017.
  */
class ExitFlowSpec  extends FlatSpec with Matchers {

  "An ExitFlow " should "get the start Element from the ExitCluster " in {

   ExitFlow.getStart().questionTag shouldBe "exit.officeHolder"
  }

  it should "have an exit cluster " in {
    ExitFlow.getClusterByName("exit").name shouldBe "exit"
  }

  it should "get an element by its tag name" in {
    ExitFlow.getElementByTag("exit.conditionsLiabilityLtd1").nonEmpty shouldBe true
  }

  it should "get an element bu its id and cluster id " in {
    ExitFlow.getElementById(0,0).nonEmpty shouldBe true
  }

  it should "get the next element" in {
    ExitFlow.getNext(ExitFlow.getStart()).nonEmpty shouldBe true
  }

  it should "indicate that office holder was yes and an in IR35 Decision has been reached " in {
    val result = ExitFlow.shouldAskForNext(Map(officeHolderYes), officeHolderYes)

    result.inIr35 shouldBe true
    result.continueToMainInterview shouldBe false
    result.element.isEmpty shouldBe true
    result.exitTool shouldBe false
  }

}
