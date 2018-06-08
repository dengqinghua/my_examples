package main

import "testing"

// go test -run TestSum
func TestSum(t *testing.T) {
	total := Sum(5, 5)

	if total != 5 {
		// t.Errorf; t.Fail; t.Log
		t.Errorf("Sumw was incorrect, got %d, want %d", total, 10)
	}
}

// go test -run TestSum1
func TestSum1(t *testing.T) {
	total := Sum(5, 5)

	if total != 6 {
		// t.Errorf; t.Fail; t.Log
		t.Errorf("Sumw was incorrect, got %d, want %d", total, 10)
	}
}
