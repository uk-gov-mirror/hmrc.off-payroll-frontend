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
import uk.gov.hmrc.offpayroll.PropertyFileLoader

/**
  * Created by peter on 06/01/2017.
  */
class SetupClusterSpec extends FlatSpec with Matchers {

  private val setupCluster = SetupCluster

  private val interview = PropertyFileLoader.transformMapFromQuestionTextToAnswers("setup")
  private val listOfAnswers = PropertyFileLoader.transformMapToAListOfAnswers(interview)

  private val personDoingWork = "setup.endUserRole.personDoingWork" -> "Yes"
  private val lastQuestion = "setup.ownMoreThan51Percent" -> "Yes"

  val fullInterview = List(
    personDoingWork,
    "setup.hasContractStarted" -> "No",
    "setup.provideServices.limitedCompany" -> "Yes",
    lastQuestion
  )

  "A Setup Cluster" should " be called setup" in {
    setupCluster.name shouldBe "setup"
  }

  it should "be identified as the first cluster with zero as its id" in {
    setupCluster.clusterID shouldBe 0
  }

  it should "have a collection of 14 cluster elements " in {
    setupCluster.clusterElements.size shouldBe 4
  }

  it should "ask the next question if not all questions have been answered " in {
    setupCluster.shouldAskForDecision(List(personDoingWork), personDoingWork).nonEmpty shouldBe true
  }

  it should "return an empty option of all questions answered" in {
    setupCluster.shouldAskForDecision(listOfAnswers, lastQuestion).isEmpty shouldBe true
  }

}
