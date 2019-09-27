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

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import connectors.DataCacheConnector
import controllers.BaseNavigationController
import controllers.actions._
import javax.inject.Inject
import models.sections.setup.WhichDescribesYouAnswer
import models.sections.setup.WhichDescribesYouAnswer.ClientPAYE
import navigation.SetupNavigator
import pages.sections.setup.WhichDescribesYouPage
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.{CheckYourAnswersService, CompareAnswerService, DecisionService}
import views.html.sections.setup.ToolNotNeededView

class ToolNotNeededController @Inject()(identify: IdentifierAction,
                                        getData: DataRetrievalAction,
                                        requireData: DataRequiredAction,
                                        controllerComponents: MessagesControllerComponents,
                                        view: ToolNotNeededView,
                                        checkYourAnswersService: CheckYourAnswersService,
                                        compareAnswerService: CompareAnswerService,
                                        dataCacheConnector: DataCacheConnector,
                                        decisionService: DecisionService,
                                        navigator: SetupNavigator,
                                        implicit val appConfig: FrontendAppConfig) extends BaseNavigationController(
  controllerComponents,compareAnswerService,dataCacheConnector,navigator,decisionService) with FeatureSwitching {

  def onPageLoad: Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    Ok(view(request.userAnswers.get(WhichDescribesYouPage).fold[WhichDescribesYouAnswer](ClientPAYE){ answerModel =>
      answerModel.answer
    }))
  }

  def onSubmit: Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    Redirect(controllers.routes.ExitSurveyController.redirectToExitSurvey())
  }
}
