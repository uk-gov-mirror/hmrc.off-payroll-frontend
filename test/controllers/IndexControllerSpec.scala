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

package controllers

import connectors.FakeDataCacheConnector
import controllers.actions.{DataRetrievalAction, FakeIdentifierAction}
import navigation.FakeNavigator
import play.api.mvc.Call
import play.api.test.Helpers._

class IndexControllerSpec extends ControllerSpecBase {

  val onwardRoute = Call("GET", "/foo")

  "Index Controller" when {

    "has an existing cacheMap in session" should {

      def controller(dataRetrievalAction: DataRetrievalAction = getEmptyCacheMap) = new IndexController(
        appConfig = frontendAppConfig,
        navigator = new FakeNavigator(onwardRoute),
        identify = FakeIdentifierAction,
        getData = dataRetrievalAction,
        cache = FakeDataCacheConnector,
        controllerComponents = messagesControllerComponents
      )

      lazy val result = controller().onPageLoad()(fakeRequest)

      "return SEE_OTHER for a GET" in {
        status(result) mustBe SEE_OTHER
      }

      "Redirect to the Onward URL" in {
        redirectLocation(result) mustBe Some(onwardRoute.url)
      }
    }

    "does not have an existing cacheMap in session" should {

      def controller(dataRetrievalAction: DataRetrievalAction = dontGetAnyData) = new IndexController(
        appConfig = frontendAppConfig,
        navigator = new FakeNavigator(onwardRoute),
        identify = FakeIdentifierAction,
        getData = dataRetrievalAction,
        cache = FakeDataCacheConnector,
        controllerComponents = messagesControllerComponents
      )

      lazy val result = controller().onPageLoad()(fakeRequest)

      "return SEE_OTHER for a GET" in {
        status(result) mustBe SEE_OTHER
      }

      "Redirect to the Onward URL" in {
        redirectLocation(result) mustBe Some(onwardRoute.url)
      }
    }
  }
}
