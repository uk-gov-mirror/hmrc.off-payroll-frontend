/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.offpayroll

import org.scalatest.{FlatSpec, Matchers}
import uk.gov.hmrc.offpayroll.service.{FlowService, IR35FlowService}

/**
  * Created by peter on 09/12/2016.
  */
class FlowServiceSpec extends FlatSpec with Matchers {

  val flowservice: FlowService = IR35FlowService

  "A Flow Service " should " be able to get the start of an Interview" in {
    flowservice.getStart() should not be (null)
  }

  val interview: Map[String, String] = Map("personalService.workerSentActualSubstitiute" -> "true")
  val currentElement: (String, String) = ("personalService.workerSentActualSubstitiute" -> "true")

  it should "accept an interview and the current element the result should indicate continue or return a decision" in {

    flowservice.evaluateInterview(interview, currentElement)
  }



}
