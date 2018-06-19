package golang

import (
	"math"
)

type Geomertry interface {
	Area() float64
}

type Rect struct {
	width, height float64
}

type Circle struct {
	radius float64
}

func (rect Rect) Area() float64 {
	return rect.width * rect.height
}

func (circle Circle) Area() float64 {
	return 2 * math.Pi * circle.radius
}

// 这里是用的接口 Geomertry
// 但是在 Rect 和 Circle 中未作任何的申明, 非常灵活
func MeasureArea(geomertry Geomertry) float64 {
	return geomertry.Area()
}
