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

package assets.messages.results

object OfficeHolderMessages extends BaseResultMessages {

  object Worker {
    object PAYE {
      val title = "Result"
      val heading = "Employed for tax purposes for this work"
      val whyResult_p1 = "In the ‘Worker’s Duties’ section, you answered that you will perform office holder duties for your client. Workers that perform these duties are employed for tax purposes."
      val doNext_p1 = "Download a copy of your result and show it to the person hiring you. They need to operate PAYE on your earnings."
    }

    object IR35 {
      val title = "Result"
      val heading = "The off-payroll working rules (IR35) apply to this contract"
      val whyResult_p1 = "In the ‘Worker’s Duties’ section, you answered that you will perform office holder duties for your client. Workers that perform these duties are deemed employed for tax purposes."
      val doNext_private_p1 = "Download a copy of your result to give to the feepayer. They need to operate PAYE on your earnings."
      val doNext_public_p1 = "Download a copy of your result to give to the feepayer. They need to operate PAYE on your earnings."
    }
  }

  object Hirer {
    object PAYE {
      val title = "Result"
      val heading = "Employed for tax purposes for this job"
      val whyResult_p1 = "In the ‘Worker’s Duties’ section, you answered that the worker will perform office holder duties for you. Workers that perform these duties are classed as employed for tax purposes."
      val doNext_p1 = "Ensure that you operate PAYE on earnings from this job."
    }

    object IR35 {
      val title = "Result"
      val heading = "The off-payroll working rules (IR35) apply to this contract"
      val whyResult_p1 = "In the ‘Worker’s Duties’ section, you answered that the worker will perform office holder duties for you. Workers that perform these duties are classed as employed for tax purposes."
      val doNext_private_p1 = "Currently, you don’t need to determine the employment status for tax of this contract. It is the worker’s responsibility."
      val doNext_public_p1 = "If you are the fee payer, you need to operate PAYE on earnings from this contract. If the fee payer is someone else, you need to show them this determination."
    }
  }

  object Agent {
    val title = "Result"
    val heading = "The off-payroll working rules (IR35) apply to this contract"
    val whyResult_p1 = "In the ‘The worker’s duties’ section, you answered that your candidate will act in an official position for your client. Workers that perform office holder duties are classed as employed for tax purposes."
    val doNext_p1 = "If you are the fee payer, you should tell your candidate that you will be operating PAYE on their earnings."
  }
}
