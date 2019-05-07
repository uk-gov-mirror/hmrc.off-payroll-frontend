/*
 * Copyright 2019 HM Revenue & Customs
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

package connectors

import base.SpecBase
import models.AboutYouAnswer.Worker
import models.ArrangedSubstitute.YesClientAgreed
import models.ChooseWhereWork.Workerchooses
import models.HowWorkIsDone.WorkerAgreeWithOthers
import models.HowWorkerIsPaid.HourlyDailyOrWeekly
import models.IdentifyToStakeholders.WorkAsIndependent
import models.MoveWorker.CanMoveWorkerWithPermission
import models.PutRightAtOwnCost.AsPartOfUsualRateInWorkingHours
import models.ScheduleOfWorkingHours.WorkerAgreeSchedule
import models.WorkerType.SoleTrader
import models.{DecisionResponse, ErrorResponse, ExitEnum, Interview, ResultEnum, Score, SetupEnum, WeightedAnswerEnum}
import uk.gov.hmrc.http.HeaderCarrier


class DecisionConnectorSpec extends SpecBase {

  implicit val headerCarrier: HeaderCarrier = new HeaderCarrier()

  val connector: DecisionConnector = new DecisionConnectorImpl(client, servicesConfig, frontendAppConfig)

  val decisionConnectorWiremock = new DecisionConnectorWiremock
  val emptyInterviewModel: Interview = Interview(
    "12345"
  )
  val interviewModel: Interview = Interview(
    "12345",
    Some(Worker),
    Some(false),
    Some(SoleTrader),
    Some(false),
    Some(YesClientAgreed),
    Some(false),
    Some(true),
    Some(true),
    Some(true),
    Some(CanMoveWorkerWithPermission),
    Some(WorkerAgreeWithOthers),
    Some(WorkerAgreeSchedule),
    Some(Workerchooses),
    Some(false),
    Some(false),
    Some(false),
    Some(false),
    Some(false),
    Some(HourlyDailyOrWeekly),
    Some(AsPartOfUsualRateInWorkingHours),
    Some(true),
    Some(true),
    Some(false),
    Some(WorkAsIndependent)
  )

  trait Setup {
    wireMock.stop()
    wireMock.start()
  }

  private val decisionResponseString =
    s"""
      |{
      |  "version": "1.0.0-beta",
      |  "correlationID": "12345",
      |  "score": {
      |    "personalService": "${WeightedAnswerEnum.HIGH}",
      |    "control": "${WeightedAnswerEnum.LOW}",
      |    "financialRisk": "${WeightedAnswerEnum.LOW}",
      |    "partAndParcel": "${WeightedAnswerEnum.LOW}"
      |  },
      |  "result": "${ResultEnum.UNKNOWN}"
      |}
    """.stripMargin

  val fullDecisionResponseString: String =
    s"""
      |{
      |  "version": "1.5.0-final",
      |  "correlationID": "12345",
      |  "score": {
      |    "setup": "${SetupEnum.CONTINUE}",
      |    "exit": "${ExitEnum.CONTINUE}",
      |    "personalService": "${WeightedAnswerEnum.HIGH}",
      |    "control": "${WeightedAnswerEnum.LOW}",
      |    "financialRisk": "${WeightedAnswerEnum.LOW}",
      |    "partAndParcel": "${WeightedAnswerEnum.LOW}"
      |  },
      |  "result": "${ResultEnum.SELF_EMPLOYED}"
      |}
    """.stripMargin

  val emptyInterview: String =
    s"""
       |{
       |  "version" : "1.5.0-final",
       |  "correlationID" : "12345",
       |  "interview" : {
       |    "setup" : { },
       |    "exit" : { },
       |    "personalService" : { },
       |    "control" : { },
       |    "financialRisk" : { },
       |    "partAndParcel" : { }
       |  }
       |}
     """.stripMargin

  val fullInterview: String =
    s"""
       |{
       |  "version": "1.5.0-final",
       |  "correlationID": "12345",
       |  "interview": {
       |    "setup": {
       |      "endUserRole": "personDoingWork",
       |      "hasContractStarted": "No",
       |      "provideServices": "soleTrader"
       |    },
       |    "exit": {
       |      "officeHolder": "No"
       |    },
       |    "personalService": {
       |      "workerSentActualSubstitute": "yesClientAgreed",
       |      "workerPayActualSubstitute": "No",
       |      "possibleSubstituteRejection": "wouldReject",
       |      "possibleSubstituteWorkerPay": "Yes",
       |      "wouldWorkerPayHelper": "Yes"
       |    },
       |    "control": {
       |      "engagerMovingWorker": "canMoveWorkerWithPermission",
       |      "workerDecidingHowWorkIsDone": "workerAgreeWithOthers",
       |      "whenWorkHasToBeDone": "workerAgreeSchedule",
       |      "workerDecideWhere": "workerChooses"
       |    },
       |    "financialRisk": {
       |      "workerProvidedMaterials": "No",
       |      "workerProvidedEquipment": "No",
       |      "workerUsedVehicle": "No",
       |      "workerHadOtherExpenses": "No",
       |      "expensesAreNotRelevantForRole": "No",
       |      "workerMainIncome": "incomeCalendarPeriods",
       |      "paidForSubstandardWork": "asPartOfUsualRateInWorkingHours"
       |    },
       |    "partAndParcel": {
       |      "workerReceivesBenefits": "Yes",
       |      "workerAsLineManager": "Yes",
       |      "contactWithEngagerCustomer": "No",
       |      "workerRepresentsEngagerBusiness": "workAsIndependent"
       |    }
       |  }
       |}
     """.stripMargin

  "Calling the decide connector" should {
    "return a decision based on the empty interview" in new Setup {
      decisionConnectorWiremock.mockForSuccessResponse(emptyInterview, decisionResponseString)

      import models.ResultEnum._
      import models.WeightedAnswerEnum._

      val clientResponse: Either[ErrorResponse, DecisionResponse] = await(connector.decide(emptyInterviewModel))
      clientResponse mustBe Right(DecisionResponse("1.0.0-beta", "12345",
        Score(None, None, Some(HIGH),Some(LOW),Some(LOW),Some(LOW)),
        UNKNOWN
      ))
    }
    "return a decision based on the populated interview" in new Setup {
      decisionConnectorWiremock.mockForSuccessResponse(fullInterview, fullDecisionResponseString)

      import models.ResultEnum._
      import models.WeightedAnswerEnum._

      val clientResponse: Either[ErrorResponse, DecisionResponse] = await(connector.decide(interviewModel))
      clientResponse mustBe Right(DecisionResponse("1.5.0-final", "12345",
        Score(Some(SetupEnum.CONTINUE), Some(ExitEnum.CONTINUE), Some(HIGH),Some(LOW),Some(LOW),Some(LOW)),
        SELF_EMPLOYED
      ))
    }

    "return an error if a bad request is returned" in new Setup  {
      decisionConnectorWiremock.mockForFailureResponse(emptyInterview, 400)

      val clientResponse: Either[ErrorResponse, DecisionResponse] = await(connector.decide(emptyInterviewModel))
      clientResponse mustBe Left(ErrorResponse(400, "Unexpected Response. Response: "))
    }
    "return an error if a 499 is returned" in new Setup  {
      decisionConnectorWiremock.mockForFailureResponse(emptyInterview, 499)

      val clientResponse: Either[ErrorResponse, DecisionResponse] = await(connector.decide(emptyInterviewModel))
      clientResponse mustBe Left(ErrorResponse(499, "Unexpected Response. Response: "))

    }
    "return an error a 500 is returned" in new Setup  {
      decisionConnectorWiremock.mockForFailureResponse(emptyInterview, 500)

      val clientResponse: Either[ErrorResponse, DecisionResponse] = await(connector.decide(emptyInterviewModel))
      clientResponse mustBe Left(ErrorResponse(500, "Unexpected Response. Response: "))

    }
  }
}