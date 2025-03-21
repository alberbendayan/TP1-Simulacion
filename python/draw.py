import pygame as pg
import random as rnd

bg = (255, 255, 255)
grid = (200, 200, 200)
green = (0, 255, 0)
red = (255, 0, 0)

screen_size = (600, 600)
static_file_name = "static.txt"
dynamic_file_name = "dynamic.txt"


class Particle:
    def __init__(self, x, y, r, p):
        self.x = x
        self.y = y
        self.r = r
        self.p = p

    def __str__(self):
        return f"Particle(x={self.x}, y={self.y}, r={self.r}, p={self.p})"

    def __repr__(self):
        return self.__str__()


def read_input_files():
    with open(static_file_name, "r") as static_file, open(dynamic_file_name, "r") as dynamic_file:
        n = int(static_file.readline())
        l = int(static_file.readline())
        dynamic_file.readline()
        particles = []
        for _ in range(n):
            static = static_file.readline().split()
            dynamic = dynamic_file.readline().split()
            particles.append(
                Particle(
                    x=float(dynamic[0]),
                    y=float(dynamic[1]),
                    r=float(static[0]),
                    p=float(static[1]),
                )
            )

    return n, l, particles


def read_neighbors_file():
    with open("output.txt", "r") as neighbors_file:
        neighbors = []
        for line in neighbors_file:
            neighbors.append(list(map(int, line[1:-2].split())))

    return neighbors


def main():
    pg.init()

    screen = pg.display.set_mode(screen_size)
    clock = pg.time.Clock()

    n, l, particles = read_input_files()
    neighbors = read_neighbors_file()

    m = 20
    rc = l/m

    width_ratio = screen_size[0] / l
    height_ratio = screen_size[1] / l

    selected = rnd.randint(0, n - 1)

    while True:
        clock.tick(60)
        screen.fill(bg)

        for i in range(0, l, l // m):
            pg.draw.line(
                screen,
                grid,
                (0, i * height_ratio),
                (screen_size[0], i * height_ratio),
            )
            pg.draw.line(
                screen,
                grid,
                (i * width_ratio, 0),
                (i * width_ratio, screen_size[1]),
            )

        for p in particles:
            pg.draw.circle(
                screen,
                (0, 0, 0),
                (int(p.x * width_ratio), int(p.y * height_ratio)),
                int(p.r * width_ratio),
            )

        for i, neighbor in enumerate(neighbors[selected]):
            if i == 0:
                surface = pg.Surface(screen_size, pg.SRCALPHA)
                pg.draw.circle(
                    surface,
                    (0, 255, 0, 80),
                    (
                        int(particles[neighbor].x * width_ratio),
                        int(particles[neighbor].y * height_ratio),
                    ),
                    int(rc * width_ratio),
                )
                screen.blit(surface, (0, 0))

            pg.draw.circle(
                screen,
                green if i == 0 else red,
                (
                    int(particles[neighbor].x * width_ratio),
                    int(particles[neighbor].y * height_ratio),
                ),
                int(particles[neighbor].r * width_ratio),
                2,
            )

        for event in pg.event.get():
            if event.type == pg.QUIT:
                pg.quit()

            if event.type == pg.KEYDOWN:
                if event.key == pg.K_SPACE:
                    selected = rnd.randint(0, n - 1)

        pg.display.flip()


if __name__ == "__main__":
    main()
