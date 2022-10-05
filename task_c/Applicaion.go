package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Barrier struct {
	n      int
	c1, c2 int
	w1, w2 sync.WaitGroup
	mx     sync.Mutex
}

func NewBarrier(n int) *Barrier {
	b := new(Barrier)
	b.n = n
	b.w1.Add(n)
	return b
}

func (b *Barrier) awaitFirst() {
	b.w1.Done()
	b.w1.Wait()
	b.mx.Lock()
	if b.c1 == 0 {
		b.w2.Add(3)
		b.c1++
	} else if b.c1 == b.n-1 {
		b.c1 = 0
	} else {
		b.c1++
	}
	b.mx.Unlock()
}

func (b *Barrier) awaitSecond() {
	b.w2.Done()
	b.w2.Wait()
	b.mx.Lock()
	if b.c2 == 0 {
		b.w1.Add(3)
		b.c2++
	} else if b.c2 == b.n-1 {
		b.c2 = 0
	} else {
		b.c2++
	}
	b.mx.Unlock()
}

func sumOfArray(array []int) int {
	res := 0
	for i := 0; i < len(array); i++ {
		res += array[i]
	}
	return res
}

func areAllEqual(array []int) bool {
	flag := true
	first := array[0]
	for i := 0; i < len(array); i++ {
		if array[i] != first {
			flag = false
			break
		}
	}
	return flag
}

func goroutine(array, results []int, thisI int, barrier *Barrier, f *sync.WaitGroup) {
	results[thisI] = sumOfArray(array)
	for true {
		if thisI == 0 {
			fmt.Println(results)
		}
		if areAllEqual(results) {
			break
		}
		barrier.awaitFirst()

		randI := rand.Intn(len(array))
		randN := []int{-1, 1}[rand.Intn(2)]
		array[randI] += randN
		results[thisI] = sumOfArray(array)
		//fmt.Println("Second ", thisI+1)
		barrier.awaitSecond()
	}
	f.Done()
}

func main() {
	const N = 3
	const K = 5

	rand.Seed(time.Now().Unix())
	var wg sync.WaitGroup
	wg.Add(N)
	b := NewBarrier(N)

	results := make([]int, N)

	for i := 0; i < N; i++ {
		array := make([]int, K)
		for k := 0; k < K; k++ {
			array[k] = rand.Intn(3)
		}
		go goroutine(array, results, i, b, &wg)
	}

	wg.Wait()
}
