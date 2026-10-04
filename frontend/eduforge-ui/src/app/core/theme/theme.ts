import { DOCUMENT } from '@angular/common';
import { Injectable, inject, signal } from '@angular/core';

export type Theme = 'light' | 'dark';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {

  private readonly document = inject(DOCUMENT);

  readonly theme = signal<Theme>(this.getInitialTheme());

  constructor() {
    this.applyTheme(this.theme());
  }

  toggle(): void {
    const nextTheme: Theme =
      this.theme() === 'light' ? 'dark' : 'light';

    this.theme.set(nextTheme);
    this.applyTheme(nextTheme);

    localStorage.setItem('eduforge-theme', nextTheme);
  }

  setTheme(theme: Theme): void {
    this.theme.set(theme);
    this.applyTheme(theme);

    localStorage.setItem('eduforge-theme', theme);
  }

  isDark(): boolean {
    return this.theme() === 'dark';
  }

  private applyTheme(theme: Theme): void {
    const root = this.document.documentElement;

    root.classList.toggle('dark-theme', theme === 'dark');
    root.classList.toggle('light-theme', theme === 'light');
  }

  private getInitialTheme(): Theme {
    const savedTheme = localStorage.getItem('eduforge-theme');

    if (savedTheme === 'dark' || savedTheme === 'light') {
      return savedTheme;
    }

    return 'light';
  }
}