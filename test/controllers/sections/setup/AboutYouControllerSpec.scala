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

package controllers.sections.setup

import akka.util.ByteString
import config.SessionKeys
import config.featureSwitch.OptimisedFlow
import controllers.ControllerSpecBase
import controllers.actions._
import forms.{AboutYouFormProvider, WhichDescribesYouFormProvider}
import models._
import org.mockito.Matchers
import org.mockito.Mockito.when
import pages.sections.setup.{AboutYouPage, WhichDescribesYouPage}
import play.api.data.Form
import play.api.http.HttpEntity
import play.api.libs.json._
import play.api.mvc.{Call, ResponseHeader, Result}
import play.api.test.Helpers._
import uk.gov.hmrc.http.cache.client.CacheMap
import utils.SessionUtils._
import views.html.sections.setup.WhichDescribesYouView
import views.html.subOptimised.sections.setup.AboutYouView

import scala.concurrent.Future

class AboutYouControllerSpec extends ControllerSpecBase {

  val aboutYouFormProvider = new AboutYouFormProvider()
  val aboutYouForm = aboutYouFormProvider()
  val whichDescribesYouFormProvider = new WhichDescribesYouFormProvider()
  val whichDescribesYouForm = whichDescribesYouFormProvider()

  val aboutYouview = injector.instanceOf[AboutYouView]
  val whichDescribesYouview = injector.instanceOf[WhichDescribesYouView]

  def controller(dataRetrievalAction: DataRetrievalAction = getEmptyCacheMap) = new AboutYouController(
    appConfig = frontendAppConfig,
    identify = FakeIdentifierAction,
    getData = dataRetrievalAction,
    requireData = new DataRequiredActionImpl(messagesControllerComponents),
    aboutYouFormProvider = new AboutYouFormProvider(),
    whichDescribesYouFormProvider = new WhichDescribesYouFormProvider(),
    controllerComponents = messagesControllerComponents,
    aboutYouView = aboutYouview,
    whichDescribesYouView = whichDescribesYouview,
    controllerHelper = controllerHelper
  )


  "AboutYou Controller" when {

    "Optimised Feature Switch is disabled" must {

      def viewAsString(form: Form[_] = aboutYouForm) = aboutYouview(form, NormalMode)(fakeRequest, messages, frontendAppConfig).toString

      "return OK and the correct view for a GET" in {
        val result = controller().onPageLoad(NormalMode)(fakeRequest)

        status(result) mustBe OK
        contentAsString(result) mustBe viewAsString()
      }

      "populate the view correctly on a GET when the question has previously been answered" in {
        val validData = Map(AboutYouPage.toString -> Json.toJson(Answers(AboutYouAnswer.values.head,0)))
        val getRelevantData = new FakeDataRetrievalAction(Some(CacheMap(cacheMapId, validData)))

        val result = controller(getRelevantData).onPageLoad(NormalMode)(fakeRequest)

        contentAsString(result) mustBe viewAsString(aboutYouForm.fill(AboutYouAnswer.values.head))
      }

      "redirect to the next page when valid data is submitted" in {
        val validCacheMap = CacheMap(cacheMapId, Map(AboutYouPage.toString -> Json.toJson(Answers(AboutYouAnswer.values.head,0))))
        when(mockDataCacheConnector.save(Matchers.any())).thenReturn(Future.successful(validCacheMap))
        val userAnswers: UserAnswers => Call = UserAnswers => Call("/POST","/foo")
        when(mockNavigator.nextPage(Matchers.any(),Matchers.any())).thenReturn(userAnswers)
        when(mockDecisionService.decide(Matchers.any(),Matchers.any(),Matchers.any())(Matchers.any(),Matchers.any(),Matchers.any()))
          .thenReturn(Future.successful(Result(ResponseHeader(SEE_OTHER),HttpEntity.Strict(ByteString(""),None))))
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", AboutYouAnswer.values.head.toString))
        val result = controller().onSubmit(NormalMode)(postRequest)

        status(result) mustBe SEE_OTHER
        session(result).getModel[UserType](SessionKeys.userType) mustBe Some(UserType(AboutYouAnswer.values.head))
      }

      "return a Bad Request and errors when invalid data is submitted" in {
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "invalid value"))
        val boundForm = aboutYouForm.bind(Map("value" -> "invalid value"))

        val result = controller().onSubmit(NormalMode)(postRequest)

        status(result) mustBe BAD_REQUEST
        contentAsString(result) mustBe viewAsString(boundForm)
      }

      "redirect to Index Controller for a GET if no existing data is found" in {
        val result = controller(dontGetAnyData).onPageLoad(NormalMode)(fakeRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
      }

      "redirect to Index Controller for a POST if no existing data is found" in {
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "worker"))
        val result = controller(dontGetAnyData).onSubmit(NormalMode)(postRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
      }
    }

    "Optimised Feature Switch is enabled" must {

      def viewAsString(form: Form[_] = whichDescribesYouForm) = whichDescribesYouview(form, NormalMode)(fakeRequest, messages, frontendAppConfig).toString

      "return OK and the correct view for a GET" in {
        enable(OptimisedFlow)
        val result = controller().onPageLoad(NormalMode)(fakeRequest)

        status(result) mustBe OK
        contentAsString(result) mustBe viewAsString()
      }

      "populate the view correctly on a GET when the question has previously been answered" in {
        enable(OptimisedFlow)
        val validData = Map(WhichDescribesYouPage.toString -> Json.toJson(Answers(WhichDescribesYouAnswer.values.head,0)))
        val getRelevantData = new FakeDataRetrievalAction(Some(CacheMap(cacheMapId, validData)))

        val result = controller(getRelevantData).onPageLoad(NormalMode)(fakeRequest)

        contentAsString(result) mustBe viewAsString(whichDescribesYouForm.fill(WhichDescribesYouAnswer.values.head))
      }

      "redirect to the next page when valid data is submitted" in {
        enable(OptimisedFlow)
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", WhichDescribesYouAnswer.values.head.toString))

        val result = controller().onSubmit(NormalMode)(postRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some("/check-employment-status-for-tax/worker-trading-as")
        session(result).getModel[UserType](SessionKeys.userType) mustBe Some(UserType(WhichDescribesYouAnswer.values.head))
      }

      "return a Bad Request and errors when invalid data is submitted" in {
        enable(OptimisedFlow)
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "invalid value"))
        val boundForm = aboutYouForm.bind(Map("value" -> "invalid value"))

        val result = controller().onSubmit(NormalMode)(postRequest)

        status(result) mustBe BAD_REQUEST
        contentAsString(result) mustBe viewAsString(boundForm)
      }

      "redirect to Index Controller for a GET if no existing data is found" in {
        enable(OptimisedFlow)
        val result = controller(dontGetAnyData).onPageLoad(NormalMode)(fakeRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
      }

      "redirect to Index Controller for a POST if no existing data is found" in {
        enable(OptimisedFlow)
        val postRequest = fakeRequest.withFormUrlEncodedBody(("value", "worker"))
        val result = controller(dontGetAnyData).onSubmit(NormalMode)(postRequest)

        status(result) mustBe SEE_OTHER
        redirectLocation(result) mustBe Some(controllers.routes.IndexController.onPageLoad().url)
      }
    }
  }
}
