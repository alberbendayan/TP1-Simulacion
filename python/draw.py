import pygame as pg
import random as rnd

pg.init()

bg = (255, 255, 255)
fg = (0, 0, 0)
grid = (200, 200, 200)

green = (0, 255, 0)
red = (255, 0, 0)

n = 100
l = 600
m = 10

selected_idx = rnd.randint(0, n-1)

particles = []
neighbors = []

with open("input.txt", "r") as f:
    for line in f:
        particles.append(list(map(float, line.split())))

with open("output.txt", "r") as f:
    for line in f:
        neighbors.append(list(map(int, line[1:-2].split())))

selected_neighbors = neighbors[selected_idx]

screen = pg.display.set_mode((l, l))

# set bg color
screen.fill(bg)

# draw a grid of m elements
for i in range(m):
    for j in range(m):
        pg.draw.rect(screen, grid, (i*l/m, j*l/m, l/m, l/m), 1)

for i, p in enumerate(particles):
    if i == selected_idx:
        pg.draw.circle(screen, red, (int(p[1]), int(p[2])), 5)
        for j in selected_neighbors:
            pg.draw.line(screen, red, (int(p[1]), int(p[2])), (int(particles[j][1]), int(particles[j][2])))
    else:
        pg.draw.circle(screen, fg, (int(p[1]), int(p[2])), 5)

pg.display.flip()

while True:
    for event in pg.event.get():
        if event.type == pg.QUIT:
            pg.quit()
            exit()