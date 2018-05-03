class Calculator

# 这里定义的宏, 在rule中使用, 其实仅仅是正则表达式
macro
  DIGIT_MACRO     \d+
  ADD_MACRO       \+
  SUBSTRACT_MACRO \-
  MULTIPLY_MACRO  \*
  DIVIDE_MACRO    \/
  BLANK_MACRO     [\ \t]+

##
# 参考
# 1. http://testerstories.com/2012/06/a-tester-learns-rex-and-racc-part-1/
# 2. http://testerstories.com/2012/06/a-tester-learns-rex-and-racc-part-2/
#
rule
  {BLANK}
  {DIGIT_MACRO}     { [:DIGIT, text.to_i] }
  {ADD_MACRO}       { [:ADD, text] }
  {SUBSTRACT_MACRO} { [:SUBSTRACT, text] }
  {MULTIPLY_MACRO}  { [:MULTIPLY, text] }
  {DIVIDE_MACRO}    { [:DIVIDE, text] }

inner
  def tokenize(code)
    scan_setup(code)
    tokens = []
    while token = next_token
      tokens << token
    end

    tokens
  end
end
