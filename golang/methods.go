package golang

import (
	"image/color"
	"math"
)

type Point struct {
	X float64
	Y float64
}

type ColoredPoint struct {
	Point
	Color color.RGBA
}

type Path []Point

func (p Point) Distance(p1 Point) float64 {
	return math.Hypot(p1.X-p.X, p1.Y-p.Y)
}

func (path Path) Distance() float64 {
	sum := 0.0

	for i := range path {
		if i > 0 {
			sum += path[i-1].Distance(path[i])
		}
	}

	return sum
}

func (p *Point) scaleBy(factor float64) {
	p.X *= factor
	p.Y *= factor
}

func (p Point) scaleByV2(factor float64) {
	p.X *= factor
	p.Y *= factor
}
