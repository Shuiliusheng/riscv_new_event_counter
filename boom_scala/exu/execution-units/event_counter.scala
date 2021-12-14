package boom.exu

import chisel3._
import chisel3.util._

import freechips.rocketchip.config.Parameters
import freechips.rocketchip.rocket._

import boom.common._
import boom.util._


class EventCounterIO(issueWidth: Int, writeWidth: Int, comWidth: Int)(implicit p: Parameters) extends BoomBundle
{
  val event_signals  = Input(Vec(16, UInt(4.W)))
  val event_signals_high  = Input(Vec(16, UInt(4.W)))

  val read_addr = Input(Vec(issueWidth, Valid(UInt(5.W))))
  val read_data = Output(Vec(issueWidth, UInt(64.W)))

  val write_addr     = Input(Vec(writeWidth, Valid(UInt(5.W))))
  val write_data     = Input(Vec(writeWidth, UInt(64.W)))
  
  val com_write_addr = Input(Vec(comWidth, Valid(UInt(5.W))))
}


class EventCounter(issueWidth: Int, writeWidth: Int, comWidth: Int)(implicit p: Parameters) extends BoomModule
{
	val io = IO(new EventCounterIO(issueWidth, writeWidth, comWidth))
	val reg_counters = io.event_signals.zipWithIndex.map { case (e, i) => freechips.rocketchip.util.WideCounter(64, e, reset = false) }
	val reg_counters_high = io.event_signals_high.zipWithIndex.map { case (e, i) => freechips.rocketchip.util.WideCounter(64, e, reset = false) }

	//for reg counter value
	val reg_read_data = Reg(Vec(issueWidth, UInt(64.W)))
	for(i <- 0 until issueWidth){
		io.read_data(i) := RegNext(reg_read_data(i))
	}

  for (i <- 0 until issueWidth) {
    when (io.read_addr(i).valid) {
      for (w <- 0 until 16) {
        when (io.read_addr(i).bits(3,0) === w.U) {
          reg_read_data(i) := Mux(io.read_addr(i).bits(4) === 0.U, reg_counters(w), reg_counters_high(w))
        }
      }
    }
    .otherwise {
      reg_read_data(i) := 0.U
    }
  }


  val reg_write_data    = Reg(Vec(32, UInt(64.W)))
  for (i <- 0 until writeWidth) {
    when (io.write_addr(i).valid) {
      val idx = io.write_addr(i).bits
      reg_write_data(idx)   := io.write_data(i)
    }
  }

  for (i <- 0 until comWidth) {
    when (io.com_write_addr(i).valid) {
      val idx = Wire(UInt(5.W))
      val write_data = Wire(UInt(64.W))
      idx := io.com_write_addr(i).bits
      write_data := reg_write_data(idx)

      for (w <- 0 until 16) {
        when (idx(3,0) === w.U) {
          when (idx(4) === 0.U) {
            reg_counters(w) := write_data
          }
          .otherwise {
            reg_counters_high(w) := write_data
          }
        }
      }
    }
  }
}
