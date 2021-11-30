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
      switch (io.read_addr(i).bits) {
        is (0.U) { reg_read_data(i) := reg_counters(0) }
        is (1.U) { reg_read_data(i) := reg_counters(1) }
        is (2.U) { reg_read_data(i) := reg_counters(2) }
        is (3.U) { reg_read_data(i) := reg_counters(3) }
        is (4.U) { reg_read_data(i) := reg_counters(4) }
        is (5.U) { reg_read_data(i) := reg_counters(5) }
        is (6.U) { reg_read_data(i) := reg_counters(6) }
        is (7.U) { reg_read_data(i) := reg_counters(7) }
        is (8.U) { reg_read_data(i) := reg_counters(8) }
        is (9.U) { reg_read_data(i) := reg_counters(9) }
        is (10.U) { reg_read_data(i) := reg_counters(10) }
        is (11.U) { reg_read_data(i) := reg_counters(11) }
        is (12.U) { reg_read_data(i) := reg_counters(12) }
        is (13.U) { reg_read_data(i) := reg_counters(13) }
        is (14.U) { reg_read_data(i) := reg_counters(14) }
        is (15.U) { reg_read_data(i) := reg_counters(15) }

        is (16.U) { reg_read_data(i) := reg_counters(16) }
        is (17.U) { reg_read_data(i) := reg_counters(17) }
        is (18.U) { reg_read_data(i) := reg_counters(18) }
        is (19.U) { reg_read_data(i) := reg_counters(19) }
        is (20.U) { reg_read_data(i) := reg_counters(20) }
        is (21.U) { reg_read_data(i) := reg_counters(21) }
        is (22.U) { reg_read_data(i) := reg_counters(22) }
        is (23.U) { reg_read_data(i) := reg_counters(23) }
        is (24.U) { reg_read_data(i) := reg_counters(24) }
        is (25.U) { reg_read_data(i) := reg_counters(25) }
        is (26.U) { reg_read_data(i) := reg_counters(26) }
        is (27.U) { reg_read_data(i) := reg_counters(27) }
        is (28.U) { reg_read_data(i) := reg_counters(28) }
        is (29.U) { reg_read_data(i) := reg_counters(29) }
        is (30.U) { reg_read_data(i) := reg_counters(30) }
        is (31.U) { reg_read_data(i) := reg_counters(31) }
      }
    }
  }

  for (i <- 0 until writeWidth) {
    when (io.write_addr(i).valid) {
      switch (io.write_addr(i).bits) {
        is (0.U) { reg_counters(0) := io.write_data(i) }
        is (1.U) { reg_counters(1) := io.write_data(i) }
        is (2.U) { reg_counters(2) := io.write_data(i) }
        is (3.U) { reg_counters(3) := io.write_data(i) }
        is (4.U) { reg_counters(4) := io.write_data(i) }
        is (5.U) { reg_counters(5) := io.write_data(i) }
        is (6.U) { reg_counters(6) := io.write_data(i) }
        is (7.U) { reg_counters(7) := io.write_data(i) }

        is (8.U) { reg_counters(8) := io.write_data(i)  }
        is (9.U) { reg_counters(9) := io.write_data(i)  }
        is (10.U) { reg_counters(10) := io.write_data(i)  }
        is (11.U) { reg_counters(11) := io.write_data(i)  }
        is (12.U) { reg_counters(12) := io.write_data(i)  }
        is (13.U) { reg_counters(13) := io.write_data(i)  }
        is (14.U) { reg_counters(14) := io.write_data(i)  }
        is (15.U) { reg_counters(15) := io.write_data(i)  }

        is (16.U) { reg_counters(16) := io.write_data(i)  }
        is (17.U) { reg_counters(17) := io.write_data(i)  }
        is (18.U) { reg_counters(18) := io.write_data(i)  }
        is (19.U) { reg_counters(19) := io.write_data(i)  }
        is (20.U) { reg_counters(20) := io.write_data(i)  }
        is (21.U) { reg_counters(21) := io.write_data(i)  }
        is (22.U) { reg_counters(22) := io.write_data(i)  }
        is (23.U) { reg_counters(23) := io.write_data(i)  }
        is (24.U) { reg_counters(24) := io.write_data(i)  }
        is (25.U) { reg_counters(25) := io.write_data(i)  }
        is (26.U) { reg_counters(26) := io.write_data(i)  }
        is (27.U) { reg_counters(27) := io.write_data(i)  }
        is (28.U) { reg_counters(28) := io.write_data(i)  }
        is (29.U) { reg_counters(29) := io.write_data(i)  }
        is (30.U) { reg_counters(30) := io.write_data(i)  }
        is (31.U) { reg_counters(31) := io.write_data(i)  }
      }
    }
  }
}
