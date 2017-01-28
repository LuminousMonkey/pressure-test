all: hard hard/sub/sub/zero.dat hard/sub/sub/sub/fifo

hard:
	mkdir -p hard/sub/sub/sub/sub/sub/sub/sub/
	cd hard/sub/sub/sub/sub/sub/sub/sub/ && ln -s ../../../../../.. sub

hard/sub/sub/zero.dat:
	dd if=/dev/zero of=hard/sub/sub/zero.dat bs=1k count=1024k

hard/sub/sub/sub/fifo:
	mkfifo hard/sub/sub/sub/node
