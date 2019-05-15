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

package assets.messages

object LineManagerDutiesMessages extends BaseMessages {

  val subheading = "About the worker’s integration into the organisation"

  object Worker {
    val heading = "Are you responsible for any of these duties for the end client?"
    val title = heading
    val b1 = "Hiring workers"
    val b2 = "Dismissing workers"
    val b3 = "Delivering appraisals"
    val b4 = "Deciding how much to pay someone"
  }

  object Hirer {
    val heading = "Is the worker responsible for any of these duties for your organisation?"
    val title = heading
    val b1 = "Hiring workers"
    val b2 = "Dismissing workers"
    val b3 = "Delivering appraisals"
    val b4 = "Deciding how much to pay someone"
  }

  object NonTailored {
    val heading = "Is the worker responsible for any of these duties for the end client?"
    val title = heading
    val b1 = "Hiring workers"
    val b2 = "Dismissing workers"
    val b3 = "Delivering appraisals"
    val b4 = "Deciding how much to pay someone"
  }

}