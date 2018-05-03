class Calculator
rule
# expression 仅仅是一个名词, 可以起名为abc 都行
# expression 可以被复用
# ":"后面是代表各种不同的情况
# DIGIT 为 'calculator.rex' 文件中对应的 :DIGIT, 这样
# racc就可以拿到rex解析后的数据
#
# racc 和 rex 的关系, 可以参考 [这里](http://epaperpress.com/lexandyacc/intro.html)
  expression
    :
    DIGIT
    | DIGIT ADD DIGIT { return val[0] + val[2] }
    | DIGIT SUBSTRACT DIGIT { return val[0] - val[2] }
    | DIGIT MULTIPLY DIGIT { return val[0] * val[2] }
    | DIGIT DIVIDE DIGIT { return val[0] / val[2] }
end

---- header
require_relative 'calculator.rex'

---- inner
  def parse(input)
    scan_str(input)
  end
