package co.ledger.wallet.core.wallet.ripple.api

import java.util.Date

import co.ledger.wallet.core.net.HttpClient
import co.ledger.wallet.core.wallet.ripple.Block
import org.json.JSONObject

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  *
  * BlockRestClient
  * ledger-wallet-ripple-chrome
  *
  * Created by Pierre Pollastri on 16/06/2016.
  *
  * The MIT License (MIT)
  *
  * Copyright (c) 2016 Ledger
  *
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  *
  * The above copyright notice and this permission notice shall be included in all
  * copies or substantial portions of the Software.
  *
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  * SOFTWARE.
  *
  */
abstract class AbstractBlockRestClient(http: HttpClient) {

  def currentBlock(): Future[Block] = http.get("/blocks/current").json.map({case (json, _) => new JsonBlock(json)})

  def jsonToBlock(json: JSONObject) = new JsonBlock(json)

  def stringToDate(string: String): Date

  class JsonBlock(json: JSONObject) extends Block {
    override def hash: String = json.getString("hash")
    override def closeTime: Date = stringToDate(json.getString("close_time"))
    override def seqNum: String = json.getString("seqNum")
    override def ledgerIndex: Long = json.getLong("ledger_index")
    override def toString: String = super.toString
    override def totalCoins: String = json.getString("totalCoins")
  }
}