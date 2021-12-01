package boom.exu

import chisel3._
import chisel3.util._

import freechips.rocketchip.config.Parameters
import freechips.rocketchip.rocket._

import boom.common._
import boom.util._


class EventCounterIO(issueWidth: Int, writeWidth: Int)(implicit p: Parameters) extends BoomBundle
{
  val event_signals  = Input(Vec(32, UInt(4.W)))

  val read_addr = Input(Vec(issueWidth, Valid(UInt(5.W))))
  val read_data = Output(Vec(issueWidth, UInt(64.W)))

  val write_addr = Input(Vec(writeWidth, Valid(UInt(5.W))))
  val write_data = Input(Vec(writeWidth, UInt(64.W)))
}


class EventCounter(issueWidth: Int, writeWidth: Int)(implicit p: Parameters) extends BoomModule
{
  val io = IO(new EventCounterIO(issueWidth, writeWidth))
  val reg_counters = io.event_signals.zipWithIndex.map { case (e, i) => freechips.rocketchip.util.WideCounter(64, e, reset = false) }

  //for reg counter value
  val reg_read_data = Reg(Vec(issueWidth, UInt(64.W)))
  for(i <- 0 until issueWidth){
    io.read_data(i) := RegNext(reg_read_data(i))
  }

  for (i <- 0 until issueWidth) {
    when (io.read_addr(i).valid) {
      when (io.read_addr(i).bits === 0.U) { reg_read_data(i) := reg_counters(0) }
      when (io.read_addr(i).bits === 1.U) { reg_read_data(i) := reg_counters(1) }
      when (io.read_addr(i).bits === 2.U) { reg_read_data(i) := reg_counters(2) }
      when (io.read_addr(i).bits === 3.U) { reg_read_data(i) := reg_counters(3) }
      when (io.read_addr(i).bits === 4.U) { reg_read_data(i) := reg_counters(4) }
      when (io.read_addr(i).bits === 5.U) { reg_read_data(i) := reg_counters(5) }
      when (io.read_addr(i).bits === 6.U) { reg_read_data(i) := reg_counters(6) }
      when (io.read_addr(i).bits === 7.U) { reg_read_data(i) := reg_counters(7) }
      when (io.read_addr(i).bits === 8.U) { reg_read_data(i) := reg_counters(8) }
      when (io.read_addr(i).bits === 9.U) { reg_read_data(i) := reg_counters(9) }
      when (io.read_addr(i).bits === 10.U) { reg_read_data(i) := reg_counters(10) }
      when (io.read_addr(i).bits === 11.U) { reg_read_data(i) := reg_counters(11) }
      when (io.read_addr(i).bits === 12.U) { reg_read_data(i) := reg_counters(12) }
      when (io.read_addr(i).bits === 13.U) { reg_read_data(i) := reg_counters(13) }
      when (io.read_addr(i).bits === 14.U) { reg_read_data(i) := reg_counters(14) }
      when (io.read_addr(i).bits === 15.U) { reg_read_data(i) := reg_counters(15) }

      when (io.read_addr(i).bits === 16.U) { reg_read_data(i) := reg_counters(16) }
      when (io.read_addr(i).bits === 17.U) { reg_read_data(i) := reg_counters(17) }
      when (io.read_addr(i).bits === 18.U) { reg_read_data(i) := reg_counters(18) }
      when (io.read_addr(i).bits === 19.U) { reg_read_data(i) := reg_counters(19) }
      when (io.read_addr(i).bits === 20.U) { reg_read_data(i) := reg_counters(20) }
      when (io.read_addr(i).bits === 21.U) { reg_read_data(i) := reg_counters(21) }
      when (io.read_addr(i).bits === 22.U) { reg_read_data(i) := reg_counters(22) }
      when (io.read_addr(i).bits === 23.U) { reg_read_data(i) := reg_counters(23) }
      when (io.read_addr(i).bits === 24.U) { reg_read_data(i) := reg_counters(24) }
      when (io.read_addr(i).bits === 25.U) { reg_read_data(i) := reg_counters(25) }
      when (io.read_addr(i).bits === 26.U) { reg_read_data(i) := reg_counters(26) }
      when (io.read_addr(i).bits === 27.U) { reg_read_data(i) := reg_counters(27) }
      when (io.read_addr(i).bits === 28.U) { reg_read_data(i) := reg_counters(28) }
      when (io.read_addr(i).bits === 29.U) { reg_read_data(i) := reg_counters(29) }
      when (io.read_addr(i).bits === 30.U) { reg_read_data(i) := reg_counters(30) }
      when (io.read_addr(i).bits === 31.U) { reg_read_data(i) := reg_counters(31) }
    }
  }

  for (i <- 0 until writeWidth) {
    when (io.write_addr(i).valid) {
      when (io.write_addr(i).bits === 0.U) { reg_counters(0) := io.write_data(i) }
      when (io.write_addr(i).bits === 1.U) { reg_counters(1) := io.write_data(i) }
      when (io.write_addr(i).bits === 2.U) { reg_counters(2) := io.write_data(i) }
      when (io.write_addr(i).bits === 3.U) { reg_counters(3) := io.write_data(i) }
      when (io.write_addr(i).bits === 4.U) { reg_counters(4) := io.write_data(i) }
      when (io.write_addr(i).bits === 5.U) { reg_counters(5) := io.write_data(i) }
      when (io.write_addr(i).bits === 6.U) { reg_counters(6) := io.write_data(i) }
      when (io.write_addr(i).bits === 7.U) { reg_counters(7) := io.write_data(i) }

      when (io.write_addr(i).bits === 8.U) { reg_counters(8) := io.write_data(i)  }
      when (io.write_addr(i).bits === 9.U) { reg_counters(9) := io.write_data(i)  }
      when (io.write_addr(i).bits === 10.U) { reg_counters(10) := io.write_data(i)  }
      when (io.write_addr(i).bits === 11.U) { reg_counters(11) := io.write_data(i)  }
      when (io.write_addr(i).bits === 12.U) { reg_counters(12) := io.write_data(i)  }
      when (io.write_addr(i).bits === 13.U) { reg_counters(13) := io.write_data(i)  }
      when (io.write_addr(i).bits === 14.U) { reg_counters(14) := io.write_data(i)  }
      when (io.write_addr(i).bits === 15.U) { reg_counters(15) := io.write_data(i)  }

      when (io.write_addr(i).bits === 16.U) { reg_counters(16) := io.write_data(i)  }
      when (io.write_addr(i).bits === 17.U) { reg_counters(17) := io.write_data(i)  }
      when (io.write_addr(i).bits === 18.U) { reg_counters(18) := io.write_data(i)  }
      when (io.write_addr(i).bits === 19.U) { reg_counters(19) := io.write_data(i)  }
      when (io.write_addr(i).bits === 20.U) { reg_counters(20) := io.write_data(i)  }
      when (io.write_addr(i).bits === 21.U) { reg_counters(21) := io.write_data(i)  }
      when (io.write_addr(i).bits === 22.U) { reg_counters(22) := io.write_data(i)  }
      when (io.write_addr(i).bits === 23.U) { reg_counters(23) := io.write_data(i)  }
      when (io.write_addr(i).bits === 24.U) { reg_counters(24) := io.write_data(i)  }
      when (io.write_addr(i).bits === 25.U) { reg_counters(25) := io.write_data(i)  }
      when (io.write_addr(i).bits === 26.U) { reg_counters(26) := io.write_data(i)  }
      when (io.write_addr(i).bits === 27.U) { reg_counters(27) := io.write_data(i)  }
      when (io.write_addr(i).bits === 28.U) { reg_counters(28) := io.write_data(i)  }
      when (io.write_addr(i).bits === 29.U) { reg_counters(29) := io.write_data(i)  }
      when (io.write_addr(i).bits === 30.U) { reg_counters(30) := io.write_data(i)  }
      when (io.write_addr(i).bits === 31.U) { reg_counters(31) := io.write_data(i)  }
    }
  }
}
