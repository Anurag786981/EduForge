export interface DashboardStat {
  title: string;
  value: number | string;
  change?: number;
  changeLabel?: string;
  trend?: 'positive' | 'negative' | 'neutral';
  placeholder?: boolean;
  icon: string;
}