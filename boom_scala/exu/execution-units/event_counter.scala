package boom.exu

import chisel3._
import chisel3.util._

import freechips.rocketchip.config.Parameters
import freechips.rocketchip.rocket._

import boom.common._
import boom.util._


class EventCounterIO(issueWidth: Int, writeWidth: Int)(implicit p: Parameters) extends BoomBundle
{
  val event_signals  = Input(Vec(8, UInt(4.W)))

  val read_addr = Input(Vec(issueWidth, Valid(UInt(5.W))))
  val read_data = Output(Vec(issueWidth, UInt(64.W)))

  val write_addr = Input(Vec(writeWidth, Valid(UInt(5.W))))
  val write_data = Input(Vec(writeWidth, UInt(64.W)))
}


class EventCounter(issueWidth: Int, writeWidth: Int)(implicit p: Parameters) extends BoomModule
{
    val io = IO(new EventCounterIO(issueWidth, writeWidth))

    // val reg_event1 = freechips.rocketchip.util.WideCounter(64, io.event_signals(0))
    // val reg_event2 = freechips.rocketchip.util.WideCounter(64, io.event_signals(1))
    // val reg_event3 = freechips.rocketchip.util.WideCounter(64, io.event_signals(2))
    // val reg_event4 = freechips.rocketchip.util.WideCounter(64, io.event_signals(3))
    // val reg_event5 = freechips.rocketchip.util.WideCounter(64, io.event_signals(4))
    // val reg_event6 = freechips.rocketchip.util.WideCounter(64, io.event_signals(5))
    // val reg_event7 = freechips.rocketchip.util.WideCounter(64, io.event_signals(6))
    // val reg_event8 = freechips.rocketchip.util.WideCounter(64, io.event_signals(7))

    val reg_counters = io.event_signals.zipWithIndex.map { case (e, i) => freechips.rocketchip.util.WideCounter(64, e, reset = false) }

    val debug_cycles = freechips.rocketchip.util.WideCounter(32)
    // for(i <- 0 until 8){
    //     printf("cycle: %d, count %d: %d\n", debug_cycles.value, i.U, reg_counters(i))
    // }

    //for reg counter value
    val reg_read_data = Reg(Vec(issueWidth, UInt(64.W)))
    for(i <- 0 until issueWidth){
        io.read_data(i) := RegNext(reg_read_data(i))
        when(RegNext(io.read_addr(i).valid)){
            printf("read event last valid, cycle: %d, idx: %d, addr: %d, data0: %d, data1: %d\n", debug_cycles.value, i.U, RegNext(io.read_addr(i).bits), io.read_data(0), io.read_data(1))
        }
    }

    for(i <- 0 until issueWidth){
        // when(RegNext(io.read_addr(i).valid)){
        //     switch (RegNext(io.read_addr(i).bits)){
        when(io.read_addr(i).valid){
            switch (io.read_addr(i).bits){
                // is (0.U) { io.read_data(i) := reg_counters(0) }
                // is (1.U) { io.read_data(i) := reg_counters(1) }
                // is (2.U) { io.read_data(i) := reg_counters(2) }
                // is (3.U) { io.read_data(i) := reg_counters(3) }
                // is (4.U) { io.read_data(i) := reg_counters(4) }
                // is (5.U) { io.read_data(i) := reg_counters(5) }
                // is (6.U) { io.read_data(i) := reg_counters(6) }
                // is (7.U) { io.read_data(i) := reg_counters(7) }
                is (0.U) { reg_read_data(i) := reg_counters(0) }
                is (1.U) { reg_read_data(i) := reg_counters(1) }
                is (2.U) { reg_read_data(i) := reg_counters(2) }
                is (3.U) { reg_read_data(i) := reg_counters(3) }
                is (4.U) { reg_read_data(i) := reg_counters(4) }
                is (5.U) { reg_read_data(i) := reg_counters(5) }
                is (6.U) { reg_read_data(i) := reg_counters(6) }
                is (7.U) { reg_read_data(i) := reg_counters(7) }
            }
            printf("read event valid, cycle: %d, idx: %d, addr: %d, data0: %d, data1: %d\n", debug_cycles.value, i.U, io.read_addr(i).bits, reg_counters(0), reg_counters(1))
        }
    }

    for(i <- 0 until writeWidth){
        when(io.write_addr(i).valid){
            switch (io.write_addr(i).bits){
                is (0.U) { reg_counters(0) := io.write_data(i) }
                is (1.U) { reg_counters(1) := io.write_data(i) }
                is (2.U) { reg_counters(2) := io.write_data(i) }
                is (3.U) { reg_counters(3) := io.write_data(i) }
                is (4.U) { reg_counters(4) := io.write_data(i) }
                is (5.U) { reg_counters(5) := io.write_data(i) }
                is (6.U) { reg_counters(6) := io.write_data(i) }
                is (7.U) { reg_counters(7) := io.write_data(i) }
            }
            printf("write event valid, cycle: %d, idx: %d, addr: %d, data: %d, counter1: %d, counter2: %d, counter3: %d\n", debug_cycles.value, i.U, io.write_addr(i).bits, io.write_data(i), reg_counters(0), reg_counters(1), reg_counters(2))
        }
    }
}
