typedef enum { opNone, opIn, opOut } operationT;

module mystery
  # (parameter depth, parameter width)
   (input  clk,
    input  rst,
    input  operationT op,
    input  logic [width-1:0] dataIn,
    output logic [width-1:0] dataOut,
    output logic empty,
    output logic full,
    output logic error);
    
    logic [width-1:0] mem[depth-1:0];
    reg[$clog2(depth-1)+1:0] head;
    // where $clog2(x) = ceiling(log_base_2(x))
    
    always_comb
      begin
        full    = head>=depth;
        empty   = head==0;
        error   = ((op==opIn) && full) || ((op==opOut) && empty);
        dataOut = empty ? -1 : mem[head-1];
      end
      
    always @(posedge clk)
      if(rst)
        head <= 0;
      else
        if(!error)
          case(op)
            opIn: begin head <= head+1; mem[head] <= dataIn; end
            opOut:      head <= head-1;
          endcase // case (op)
endmodule 
