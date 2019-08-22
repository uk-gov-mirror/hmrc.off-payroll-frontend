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

package views.subOptimised.sections.personalService

import assets.messages.{ArrangedSubstituteMessages, SubHeadingMessages}
import forms.ArrangedSubstituteFormProvider
import models.requests.DataRequest
import models.{ArrangedSubstitute, NormalMode}
import play.api.data.Form
import views.behaviours.ViewBehaviours
import views.html.subOptimised.sections.personalService.ArrangedSubstituteView

class ArrangedSubstituteViewSpec extends ViewBehaviours {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "arrangedSubstitute"

  val form = new ArrangedSubstituteFormProvider()()(fakeDataRequest, frontendAppConfig)

  val view = injector.instanceOf[ArrangedSubstituteView]

  def createView = () => view(form, NormalMode)(fakeRequest, messages, frontendAppConfig)

  def createViewUsingForm = (form: Form[_]) => view(form, NormalMode)(fakeRequest, messages, frontendAppConfig)

  def createViewWithRequest = (req: DataRequest[_]) => view(form, NormalMode)(req, messages, frontendAppConfig)

  "ArrangedSubstitute view" must {
    behave like normalPage(createView, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createView)

    "If the user type is of Worker" should {

      lazy val document = asDocument(createViewWithRequest(workerFakeDataRequest))

      "have the correct title" in {
        document.title mustBe title(ArrangedSubstituteMessages.Worker.title, Some(SubHeadingMessages.SubOptimised.personalService))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text mustBe ArrangedSubstituteMessages.Worker.heading
      }

      "have the correct subheading" in {
        document.select(Selectors.subheading).text mustBe SubHeadingMessages.SubOptimised.personalService
      }

      "have the correct hints" in {
        document.select(Selectors.p(1)).text mustBe ArrangedSubstituteMessages.Worker.p1
        document.select(Selectors.bullet(1)).text mustBe ArrangedSubstituteMessages.Worker.b1
        document.select(Selectors.bullet(2)).text mustBe ArrangedSubstituteMessages.Worker.b2
        document.select(Selectors.bullet(3)).text mustBe ArrangedSubstituteMessages.Worker.b3
        document.select(Selectors.bullet(4)).text mustBe ArrangedSubstituteMessages.Worker.b4
        document.select(Selectors.bullet(5)).text mustBe ArrangedSubstituteMessages.Worker.b5
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ArrangedSubstituteMessages.Worker.yesClientAgreed
        document.select(Selectors.multichoice(2)).text mustBe ArrangedSubstituteMessages.Worker.yesClientNotAgreed
        document.select(Selectors.multichoice(3)).text mustBe ArrangedSubstituteMessages.Worker.no
      }
    }

    "If the user type is of Hirer" should {

      lazy val document = asDocument(createViewWithRequest(hirerFakeDataRequest))

      "have the correct title" in {
        document.title mustBe title(ArrangedSubstituteMessages.Hirer.title, Some(SubHeadingMessages.SubOptimised.personalService))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text mustBe ArrangedSubstituteMessages.Hirer.heading
      }

      "have the correct subheading" in {
        document.select(Selectors.subheading).text mustBe SubHeadingMessages.SubOptimised.personalService
      }

      "have the correct hints" in {
        document.select(Selectors.p(1)).text mustBe ArrangedSubstituteMessages.Hirer.p1
        document.select(Selectors.bullet(1)).text mustBe ArrangedSubstituteMessages.Hirer.b1
        document.select(Selectors.bullet(2)).text mustBe ArrangedSubstituteMessages.Hirer.b2
        document.select(Selectors.bullet(3)).text mustBe ArrangedSubstituteMessages.Hirer.b3
        document.select(Selectors.bullet(4)).text mustBe ArrangedSubstituteMessages.Hirer.b4
        document.select(Selectors.bullet(5)).text mustBe ArrangedSubstituteMessages.Hirer.b5
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ArrangedSubstituteMessages.Hirer.yesClientAgreed
        document.select(Selectors.multichoice(2)).text mustBe ArrangedSubstituteMessages.Hirer.yesClientNotAgreed
        document.select(Selectors.multichoice(3)).text mustBe ArrangedSubstituteMessages.Hirer.no
      }
    }

    "If the user type is of Agency" should {

      lazy val document = asDocument(createViewWithRequest(agencyFakeDataRequest))

      "have the correct title" in {
        document.title mustBe title(ArrangedSubstituteMessages.NonTailored.title, Some(SubHeadingMessages.SubOptimised.personalService))
      }

      "have the correct heading" in {
        document.select(Selectors.heading).text mustBe ArrangedSubstituteMessages.NonTailored.heading
      }

      "have the correct subheading" in {
        document.select(Selectors.subheading).text mustBe SubHeadingMessages.SubOptimised.personalService
      }

      "have the correct hints" in {
        document.select(Selectors.p(1)).text mustBe ArrangedSubstituteMessages.NonTailored.p1
        document.select(Selectors.bullet(1)).text mustBe ArrangedSubstituteMessages.NonTailored.b1
        document.select(Selectors.bullet(2)).text mustBe ArrangedSubstituteMessages.NonTailored.b2
        document.select(Selectors.bullet(3)).text mustBe ArrangedSubstituteMessages.NonTailored.b3
        document.select(Selectors.bullet(4)).text mustBe ArrangedSubstituteMessages.NonTailored.b4
        document.select(Selectors.bullet(5)).text mustBe ArrangedSubstituteMessages.NonTailored.b5
      }

      "have the correct radio option messages" in {
        document.select(Selectors.multichoice(1)).text mustBe ArrangedSubstituteMessages.NonTailored.yesClientAgreed
        document.select(Selectors.multichoice(2)).text mustBe ArrangedSubstituteMessages.NonTailored.yesClientNotAgreed
        document.select(Selectors.multichoice(3)).text mustBe ArrangedSubstituteMessages.NonTailored.no
      }
    }
  }

  "ArrangedSubstitute view" when {
    "rendered" must {
      "contain radio buttons for the value" in {
        val doc = asDocument(createView())
        for (option <- ArrangedSubstitute.options) {
          assertContainsRadioButton(doc, option.id, "value", option.value, false)
        }
      }
    }

    for(option <- ArrangedSubstitute.options) {
      s"rendered with a value of '${option.value}'" must {
        s"have the '${option.value}' radio button selected" in {
          val doc = asDocument(createViewUsingForm(form.bind(Map("value" -> s"${option.value}"))))
          assertContainsRadioButton(doc, option.id, "value", option.value, true)

          for(unselectedOption <- ArrangedSubstitute.options.filterNot(o => o.value == option.value)) {
            assertContainsRadioButton(doc, unselectedOption.id, "value", unselectedOption.value, false)
          }
        }
      }
    }
  }
}
